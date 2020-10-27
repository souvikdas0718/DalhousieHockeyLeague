package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.sql.Date;
import java.sql.ResultSet;

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

}
