package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IPlayerStatistics;

public class FreeAgent extends Player {
    public FreeAgent() {
        super();
    }

    public FreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        super(playerName, position, playerStatistics);

    }

    @Override
    public boolean checkPlayerValid() throws Exception {
        if (this.isPlayerNameEmpty()) {
            throw new Exception("Player name cannot be empty");
        }
        if (this.isPlayerPositionInvalid()) {
            throw new Exception("Player position must be goalie or forward or defense");
        }
        IPlayerStatistics playerStatistics = this.getPlayerStats();
        playerStatistics.checkPlayerStatistics();
        return true;
    }

}

