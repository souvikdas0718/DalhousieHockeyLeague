package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.interfaceModel.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class PlayerDB implements IPlayerDB {
    public int insertPlayer(IPlayer player, int teamId, int leagueId )  throws Exception {
        int playerId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertPlayer(?,?,?,?,?,?,?,?,?,?)");
        callproc.setParameter(1, player.getPlayerName());
        callproc.setParameter(2, player.getPosition());
        callproc.setParameter(3, player.getCaptain());
        callproc.setParameter(4, player.getPlayerStats().getAge());
        callproc.setParameter(5, player.getPlayerStats().getSkating());
        callproc.setParameter(6, player.getPlayerStats().getShooting());
        callproc.setParameter(7, player.getPlayerStats().getChecking());
        callproc.setParameter(8, player.getPlayerStats().getSaving());
        callproc.setParameter(9, teamId);
        callproc.setParameter(10, leagueId);
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

    public void insertInjuredPlayer(IPlayer player, int teamId, int leagueId )  throws Exception {
        CallStoredProcedure callproc = new CallStoredProcedure("insertInjuredPlayer(?,?,?,?,?)");
        callproc.setParameter(1, player.getPlayerName());
        IInjurySystem injurySystem = player.getInjurySystem();
        java.util.Date dateInjured=injurySystem.getInjuryDate();
        callproc.setDate(2, new Date(dateInjured.getTime()));
        callproc.setParameter(3, injurySystem.getNumberOfDaysInjured());
        callproc.setParameter(4, teamId);
        callproc.setParameter(5, leagueId);
        callproc.execute();

        callproc.cleanup();

    }

    public void insertRetiredPlayers(ILeagueObjectModel leagueObjectModel, Map<String, List<IPlayer>> playersToRetire, Map<String,List<IFreeAgent>> freeAgentsToRetire) throws Exception {
        CallStoredProcedure callproc = new CallStoredProcedure("insertVeteran(?,?,?,?)");
        for(String teamName : playersToRetire.keySet()){
            for(IPlayer player: playersToRetire.get(teamName)){
                callproc.setParameter(1, player.getPlayerName());
                IPlayerStatistics playerStats=player.getPlayerStats();
                callproc.setParameter(2, playerStats.getAge());
                callproc.setParameter(3, teamName);
                callproc.setParameter(4, leagueObjectModel.getLeagueName());

                callproc.execute();
            }

        }
        for(String leagueName : freeAgentsToRetire.keySet()){
            for(IFreeAgent freeAgent: freeAgentsToRetire.get(leagueName)){
                callproc.setParameter(1, freeAgent.getPlayerName());
                IPlayerStatistics playerStats=freeAgent.getPlayerStats();
                callproc.setParameter(2, playerStats.getAge());
                callproc.setParameter(4, leagueName);
                callproc.execute();
            }

        }


        callproc.cleanup();
    }

}
