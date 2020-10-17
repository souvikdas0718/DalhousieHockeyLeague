package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;

import java.sql.ResultSet;

public class FreeAgentDB implements IFreeAgentDB {
    public int insertFreeAgent(String playerName, String playerPosition, int leagueId )  throws Exception {
        int freeAgentId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertFreeAgent(?,?,?)");
        callproc.setParameter(1, playerName);
        callproc.setParameter(2, playerPosition);
        callproc.setParameter(3, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                freeAgentId = results.getInt("freeAgent_Id");
            }
        }
        else {
            throw new Exception("Free Agents not inserted properly");
        }

        callproc.cleanup();

        return freeAgentId;
    }

}
