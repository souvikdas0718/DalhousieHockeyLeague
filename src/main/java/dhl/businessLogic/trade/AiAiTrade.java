package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.RosterUpdaterAbstractFactory;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.importJson.interfaces.IGeneralManagerPersonalityList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class AiAiTrade implements ITradeType {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    ITradeOffer tradeOffer;
    IGameConfig gameConfig;
    Dictionary managerPersonalityList;
    ITeamRosterUpdater rosterUpdater;

    private static final Logger logger = LogManager.getLogger(AiAiTrade.class);

    public AiAiTrade(ITradeOffer tradeOffer, IGameConfig gameConfig) {
        this.tradeOffer = tradeOffer;
        this.gameConfig = gameConfig;

        rosterUpdater = RosterUpdaterAbstractFactory.instance().createAiTeamRosterUpdater();
        IGeneralManagerPersonalityList managerPersonalityObject = IGeneralManagerPersonalityList.instance(gameConfig);
        managerPersonalityList = managerPersonalityObject.getGeneralManagerPersonalityList();
    }

    public boolean isTradeAccepted() {
        double configRandomAcceptanceChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomAcceptanceChance()));
        double randomValue = Math.random();
        if (isTradeGoodForReceivingTeam(tradeOffer)) {
            return true;
        } else{
            ITeam receivingTeam = tradeOffer.getReceivingTeam();
            IGeneralManager receivingManager = receivingTeam.getGeneralManager();
            String ourManagerPersonality = receivingManager.getGeneralManagerPersonality();
            double managerModifier = (double) managerPersonalityList.get(ourManagerPersonality);
            configRandomAcceptanceChance = configRandomAcceptanceChance + managerModifier;
            if (randomValue > configRandomAcceptanceChance) {
                logger.info("Trade Accepted by "+ tradeOffer.getReceivingTeam().getTeamName());
                return true;
            }
        }
        logger.info("Trade Rejected by "+ tradeOffer.getReceivingTeam().getTeamName());
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) {
        int totalForwards = 0;
        int totalDefense = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString())){
                totalForwards = totalForwards + 1;
            }
            else if (position.equals(PlayerPosition.DEFENSE.toString())){
                totalDefense = totalDefense + 1;
            }
            else if (position.equals(PlayerPosition.GOALIE.toString())) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalForwards < TOTAL_FORWARDS || totalForwards > TOTAL_FORWARDS) {
            updatePlayers(totalForwards, PlayerPosition.FORWARD.toString(), TOTAL_FORWARDS, team, leagueObjectModel);
        }
        if (totalDefense < TOTAL_DEFENSE || totalDefense > TOTAL_DEFENSE) {
            updatePlayers(totalDefense, PlayerPosition.DEFENSE.toString(), TOTAL_DEFENSE, team, leagueObjectModel);
        }
        if (totalGoalies < TOTAL_GOALIES || totalGoalies > TOTAL_GOALIES) {
            updatePlayers(totalGoalies, PlayerPosition.GOALIE.toString(), TOTAL_GOALIES, team, leagueObjectModel);
        }
    }

    public void updatePlayers(int currentCount, String playerPosition, int validCount, ITeam team, ILeagueObjectModel leagueObjectModel) {
        if (currentCount < validCount){
            while (currentCount < validCount){
                rosterUpdater.addPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount + 1;
            }
        }else if (currentCount > validCount) {
            while (currentCount > validCount) {
                rosterUpdater.dropPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount - 1;
            }
        }
    }

    public boolean isTradeGoodForReceivingTeam(ITradeOffer tradeOffer) {
        if (getPlayerCombinedStrength(tradeOffer.getOfferingPlayers()) > getPlayerCombinedStrength(tradeOffer.getPlayersWantedInReturn())) {
            return true;
        }
        return false;
    }

    public double getPlayerCombinedStrength(ArrayList<IPlayer> players) {
        double totalStrength = 0;
        for (int i = 0; i < players.size(); i++) {
            totalStrength += players.get(i).getPlayerStrength();
        }
        return totalStrength;
    }
}
