package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.importJson.interfaces.IGeneralManagerPersonalityList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Dictionary;

public class AiAiTrade implements ITradeType {

    ILeagueObjectModel league;
    IGameConfig gameConfig;
    Dictionary managerPersonalityList;
    ITeamRosterUpdater rosterUpdater;
    private static final Logger logger = LogManager.getLogger(AiAiTrade.class);

    public AiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig) {
        this.league = league;
        this.gameConfig = gameConfig;

        rosterUpdater = RosterUpdaterAbstractFactory.instance().createAiTeamRosterUpdater();
        IGeneralManagerPersonalityList managerPersonalityObject = IGeneralManagerPersonalityList.instance(gameConfig);
        managerPersonalityList = managerPersonalityObject.getGeneralManagerPersonalityList();

    }

    public boolean isTradeAccepted(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted, ITeam receivingTeam) {
        double configRandomAcceptanceChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomAcceptanceChance()));
        double randomValue = Math.random();
        if (isTradeGoodForReceivingTeam(playersOffered, playerswanted)) {
            return true;
        } else{
            IGeneralManager receivingManager = receivingTeam.getGeneralManager();
            String ourManagerPersonality = receivingManager.getGeneralManagerPersonality();
            double managerModifier = (double) managerPersonalityList.get(ourManagerPersonality);
            configRandomAcceptanceChance = configRandomAcceptanceChance + managerModifier;
            if (randomValue > configRandomAcceptanceChance) {
                logger.info("Trade Accepted by "+ receivingTeam.getTeamName());
                return true;
            }
        }
        logger.info("Trade Rejected by "+ receivingTeam.getTeamName());
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team) {
        rosterUpdater.validateTeamRoster(team, league);
    }

    public boolean isTradeGoodForReceivingTeam(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted) {
        if (getPlayerCombinedStrength(playersOffered) > getPlayerCombinedStrength(playerswanted)) {
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
