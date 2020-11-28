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

            rosterUpdater.validateTeamRoster(team, leagueObjectModel);
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
