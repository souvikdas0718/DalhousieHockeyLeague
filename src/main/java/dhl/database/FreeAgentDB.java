package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IFreeAgentDB;
import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FreeAgentDB implements IFreeAgentDB {
    public int insertFreeAgent(IPlayer freeAgent, int leagueId) throws Exception {
        int freeAgentId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertFreeAgent(?,?,?,?,?,?,?,?,?)");
        callproc.setParameter(1, freeAgent.getPlayerName());
        callproc.setParameter(2, freeAgent.getPosition());
        IPlayerStatistics playerStatistics = freeAgent.getPlayerStats();
        callproc.setParameter(3, playerStatistics.getAge());
        callproc.setParameter(4, playerStatistics.getSkating());
        callproc.setParameter(5, playerStatistics.getShooting());
        callproc.setParameter(6, playerStatistics.getChecking());
        callproc.setParameter(7, playerStatistics.getSaving());
        callproc.setParameter(8, freeAgent.getPlayerInjuredDays());
        callproc.setParameter(9, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Free Agents not inserted properly");
        }

        while (results.next()) {
            freeAgentId = results.getInt("freeAgent_Id");
        }
        callproc.cleanup();

        return freeAgentId;
    }

    public List<IPlayer> getFreeAgentList(int leagueId) throws Exception {
        List<IPlayer> freeAgentList = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadFreeAgents(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet agentsResultSet = callAgentProc.executeWithResults();

        if (agentsResultSet == null) {
            throw new Exception("Error loading Free agents");
        }

        while (agentsResultSet.next()) {
            IPlayerStatistics playerStatistics = new PlayerStatistics(agentsResultSet.getInt("age"), agentsResultSet.getInt("skating"),
                    agentsResultSet.getInt("shooting"), agentsResultSet.getInt("checking"), agentsResultSet.getInt("saving"));
            IPlayer freeAgent = new FreeAgent(agentsResultSet.getString("playerName"), agentsResultSet.getString("playerPosition"), playerStatistics);
            freeAgent.setPlayerInjuredDays(agentsResultSet.getInt("numberOfDaysInjured"));
            freeAgentList.add(freeAgent);
        }
        callAgentProc.cleanup();
        return freeAgentList;
    }

}
