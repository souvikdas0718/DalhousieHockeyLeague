package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreeAgent extends Player {

    private static final Logger logger = LogManager.getLogger(FreeAgent.class);

    public FreeAgent() {
        super();
    }

    public FreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        super(playerName, position, playerStatistics);

    }

    public boolean checkPlayerValid() throws Exception {
        if (this.isPlayerNameEmpty()) {
            throw new Exception("Player name cannot be empty");
        }
        if (this.isPlayerPositionInvalid()) {
            logger.error(getPosition() + ":" + "is not valid position for player ->" + getPlayerName());
            throw new Exception("Player position must be goalie or forward or defense");
        }
        IPlayerStatistics playerStatistics = this.getPlayerStats();
        playerStatistics.checkPlayerStatistics();
        return true;
    }

}

