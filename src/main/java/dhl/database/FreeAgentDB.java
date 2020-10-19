package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IFreeAgentDB;
import dhl.leagueModel.interfaceModel.IFreeAgent;

import java.sql.ResultSet;

public class FreeAgentDB implements IFreeAgentDB {
    public int insertFreeAgent(IFreeAgent freeAgent, int leagueId )  throws Exception {
        int freeAgentId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertFreeAgent(?,?,?,?,?,?,?,?)");
        callproc.setParameter(1, freeAgent.getPlayerName());
        callproc.setParameter(2, freeAgent.getPosition());
        callproc.setParameter(3, freeAgent.getPlayerStats().getAge());
        callproc.setParameter(4, freeAgent.getPlayerStats().getSkating());
        callproc.setParameter(5, freeAgent.getPlayerStats().getShooting());
        callproc.setParameter(6, freeAgent.getPlayerStats().getChecking());
        callproc.setParameter(7, freeAgent.getPlayerStats().getSaving());
        callproc.setParameter(8, leagueId);
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
