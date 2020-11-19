package dhl.database;

import dhl.database.databaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ILeagueDB;

import java.sql.Date;
import java.sql.ResultSet;

public class LeagueDB implements ILeagueDB {

    public int insertLeague(String leagueName) throws Exception {
        int leagueId = 0;

        CallStoredProcedure callproc = callproc = new CallStoredProcedure("insertLeague(?)");
        callproc.setParameter(1, leagueName);
        ResultSet results = callproc.executeWithResults();
        if (null == results) {
            throw new Exception("League already exists.");
        }
        while (results.next()) {
            leagueId = results.getInt("league_Id");
        }
        callproc.cleanup();

        return leagueId;
    }

    @Override
    public void insertDate(int league_Id, int team_Id, Date season_startDate, Date current_date) throws Exception {
        CallStoredProcedure callprocDate = new CallStoredProcedure("insertDate(?,?,?,?)");
        callprocDate.setParameter(1, league_Id);
        callprocDate.setParameter(2, team_Id);
        callprocDate.setParameter(3, season_startDate);
        callprocDate.setParameter(4, current_date);
        callprocDate.cleanup();
    }

    public boolean checkIfLeagueAlreadyExists(String leagueName) throws Exception {
        boolean isexist = false;

        CallStoredProcedure callproc = new CallStoredProcedure("checkIfLeagueAlreadyExists(?,?)");
        callproc.setParameter(1, leagueName);
        callproc.registerOutputParameterBoolean(2);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Error executing check on league");
        }
        while (results.next()) {
            isexist = results.getBoolean("league_exists");
        }

        callproc.cleanup();

        return isexist;
    }

}
