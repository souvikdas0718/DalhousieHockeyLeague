package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerDB implements IPlayerDB {

    public int insertPlayer(IPlayer player, int teamId, int leagueId) throws Exception {
        int playerId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertPlayer(?,?,?,?,?,?,?,?,?,?,?)");
        callproc.setParameter(1, player.getPlayerName());
        callproc.setParameter(2, player.getPosition());
        callproc.setParameter(3, player.getCaptain());
        IPlayerStatistics playerStatistics = player.getPlayerStats();
        callproc.setParameter(4, playerStatistics.getAge());
        callproc.setParameter(5, playerStatistics.getSkating());
        callproc.setParameter(6, playerStatistics.getShooting());
        callproc.setParameter(7, playerStatistics.getChecking());
        callproc.setParameter(8, playerStatistics.getSaving());
        callproc.setParameter(9, player.getPlayerInjuredDays());
        callproc.setParameter(10, teamId);
        callproc.setParameter(11, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Player not inserted properly");
        }
        while (results.next()) {
            playerId = results.getInt("player_Id");
        }
        callproc.cleanup();

        return playerId;
    }

    public void updatePlayer(IPlayer player, String teamName, String leagueName) throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("updatePlayer(?,?,?,?,?,?,?,?,?,?,?)");
            callproc.setParameter(1, player.getPlayerName());
            callproc.setParameter(2, player.getPosition());
            callproc.setParameter(3, player.getCaptain());
            IPlayerStatistics playerStatistics = player.getPlayerStats();
            callproc.setParameter(4, playerStatistics.getAge());
            callproc.setParameter(5, playerStatistics.getSkating());
            callproc.setParameter(6, playerStatistics.getShooting());
            callproc.setParameter(7, playerStatistics.getChecking());
            callproc.setParameter(8, playerStatistics.getSaving());
            callproc.setParameter(9, player.getPlayerInjuredDays());
            callproc.setParameter(10, teamName);
            callproc.setParameter(11, leagueName);
            callproc.execute();
            callproc.cleanup();
        } catch (SQLException sqlException) {
            throw new SQLException("Update failed");
        }
    }

    public void insertRetiredPlayers(ILeagueObjectModel leagueObjectModel, Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertVeteran(?,?,?,?)");
            for (String teamName : playersToRetire.keySet()) {
                for (IPlayer player : playersToRetire.get(teamName)) {
                    callproc.setParameter(1, player.getPlayerName());
                    IPlayerStatistics playerStats = player.getPlayerStats();
                    callproc.setParameter(2, playerStats.getAge());
                    callproc.setParameter(3, teamName);
                    callproc.setParameter(4, leagueObjectModel.getLeagueName());
                    callproc.execute();
                }
            }
            for (IPlayer freeAgent : freeAgentsToRetire) {
                callproc.setParameter(1, freeAgent.getPlayerName());
                IPlayerStatistics playerStats = freeAgent.getPlayerStats();
                callproc.setParameter(2, playerStats.getAge());
                callproc.setParameter(3, "");
                callproc.setParameter(4, leagueObjectModel.getLeagueName());
                callproc.execute();
            }
            callproc.cleanup();
        } catch (Exception e) {
            throw new Exception("Retired player not inserted properly");
        }
    }

    public List<IPlayer> getPlayerList(int teamId, int leagueId) throws Exception {
        List<IPlayer> playerList = new ArrayList<>();
        CallStoredProcedure callPlayerProc = new CallStoredProcedure("loadPlayers(?,?)");
        callPlayerProc.setParameter(1, teamId);
        callPlayerProc.setParameter(2, leagueId);
        ResultSet playerResultSet = callPlayerProc.executeWithResults();

        if (playerResultSet == null) {
            throw new Exception("Error loading players");
        }

        while (playerResultSet.next()) {
            IPlayerStatistics playerStatistics = new PlayerStatistics(playerResultSet.getInt("age"), playerResultSet.getInt("skating"),
                    playerResultSet.getInt("shooting"), playerResultSet.getInt("checking"), playerResultSet.getInt("saving"));
            IPlayer player = new Player(playerResultSet.getString("playerName"), playerResultSet.getString("position"), playerResultSet.getBoolean("isCaptain"), playerStatistics);
            player.setPlayerInjuredDays(playerResultSet.getInt("numberOfDaysInjured"));
            playerList.add(player);

        }
        callPlayerProc.cleanup();
        return playerList;
    }

}
