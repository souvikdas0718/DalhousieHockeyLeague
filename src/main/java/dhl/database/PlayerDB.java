package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;

import java.sql.ResultSet;

public class PlayerDB implements IPlayerDB {
    public int insertPlayer(String playerName, String playerPosition, boolean isCaptain, boolean isFreeAgent, int teamId, int leagueId )  throws Exception {
        int playerId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertPlayer(?,?,?,?,?)");
        callproc.setParameter(1, playerName);
        callproc.setParameter(2, playerPosition);
        callproc.setParameter(3, isCaptain);
        callproc.setParameter(4, teamId);
        callproc.setParameter(5, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                playerId = results.getInt("player_Id");
            }
        }
        else {
            throw new Exception("Player not inserted properly");
        }

        callproc.cleanup();

        return playerId;
    }

}
