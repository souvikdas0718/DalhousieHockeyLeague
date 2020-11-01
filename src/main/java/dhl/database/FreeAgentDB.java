package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IFreeAgentDB;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;

import java.sql.ResultSet;

public class FreeAgentDB implements IFreeAgentDB {
    public int insertFreeAgent(IPlayer freeAgent, int leagueId )  throws Exception {
        int freeAgentId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertFreeAgent(?,?,?,?,?,?,?,?,?)");
        callproc.setParameter(1, freeAgent.getPlayerName());
        callproc.setParameter(2, freeAgent.getPosition());
        IPlayerStatistics playerStatistics=freeAgent.getPlayerStats();
        callproc.setParameter(3, playerStatistics.getAge());
        callproc.setParameter(4, playerStatistics.getSkating());
        callproc.setParameter(5, playerStatistics.getShooting());
        callproc.setParameter(6, playerStatistics.getChecking());
        callproc.setParameter(7, playerStatistics.getSaving());
        callproc.setParameter(8, freeAgent.getPlayerInjuredDays());
        callproc.setParameter(9, leagueId);
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
