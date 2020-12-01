package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.importJson.GeneralManagerPersonalityListAbstract;
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
        GeneralManagerPersonalityListAbstract managerPersonalityObject = GeneralManagerPersonalityListAbstract.instance(gameConfig);
        managerPersonalityList = managerPersonalityObject.getGeneralManagerPersonalityList();
        logger.debug("trade between two AI teams made");
    }

    public boolean isTradeAccepted(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted, ITeam receivingTeam) {
        logger.debug("Checking if trade accpeted by team: " + receivingTeam.getTeamName());
        double configRandomAcceptanceChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomAcceptanceChance()));
        double randomValue = Math.random();
        if (isTradeGoodForReceivingTeam(playersOffered, playerswanted)) {
            logger.info("Trade Accepted by team: " + receivingTeam.getTeamName());
            return true;
        } else {
            IGeneralManager receivingManager = receivingTeam.getGeneralManager();
            String ourManagerPersonality = receivingManager.getGeneralManagerPersonality();
            logger.debug(receivingTeam.getTeamName() + "'s Manager personality is:" + ourManagerPersonality);
            double managerModifier = (double) managerPersonalityList.get(ourManagerPersonality);
            configRandomAcceptanceChance = configRandomAcceptanceChance + managerModifier;
            if (randomValue > configRandomAcceptanceChance) {
                logger.info("Trade Accepted by " + receivingTeam.getTeamName());
                return true;
            }
        }
        logger.info("Trade Rejected by " + receivingTeam.getTeamName());
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team) {
        logger.debug("validating Roster After trade for team: " + team.getTeamName());
        rosterUpdater.validateTeamRoster(team, league);
    }

    public boolean isTradeGoodForReceivingTeam(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted) {

        if (getPlayerCombinedStrength(playersOffered) > getPlayerCombinedStrength(playerswanted)) {
            logger.debug("Trade not good for team");
            return true;
        }
        logger.debug("Trade good for team");
        return false;
    }

    public double getPlayerCombinedStrength(ArrayList<IPlayer> players) {
        double totalStrength = 0;
        for (int i = 0; i < players.size(); i++) {
            totalStrength += players.get(i).getPlayerStrength();
        }
        logger.debug("Combined player Stength: " + totalStrength);
        return totalStrength;
    }
}
