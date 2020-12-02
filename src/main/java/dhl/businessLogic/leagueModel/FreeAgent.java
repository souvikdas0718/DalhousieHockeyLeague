package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreeAgent extends Player {
    private static final Logger logger = LogManager.getLogger(FreeAgent.class);

    public FreeAgent() {
        super();
        logger.info("Creating Free agent object default");
    }

    public FreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        super(playerName, position, playerStatistics);
        logger.info("Creating Free agent object parameterized");

    }
}

