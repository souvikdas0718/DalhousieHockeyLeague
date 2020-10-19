package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.*;

import java.sql.Date;
import java.sql.ResultSet;

public class LeagueDB implements ILeagueDB {
    IConferenceDB iConferenceDB = new ConferenceDB();
    IDivisionDB iDivisionDB = new DivisionDB();
    ITeamDB iTeamDB = new TeamDB();
    IPlayerDB iPlayerDB = new PlayerDB();
    IFreeAgentDB iFreeAgentDB = new FreeAgentDB();

    public int insertLeague(String leagueName) throws Exception {
        int leagueId=0;

        CallStoredProcedure callproc = callproc = new CallStoredProcedure("insertLeague(?)");
        callproc.setParameter(1, leagueName);
        ResultSet results = callproc.executeWithResults();
        if (null != results) {
            while(results.next()){
                leagueId = results.getInt("league_Id");
            }
        }
        else {
            throw new Exception("League already exists.");
        }

        callproc.cleanup();

        return leagueId;
    }

    @Override
    public void insertDate(int league_Id, int team_Id, Date season_startDate, Date current_date) throws Exception {
//        int leagueId=0;
        CallStoredProcedure callprocDate = new CallStoredProcedure("insertDate(?,?,?,?)");
        callprocDate.setParameter(1, league_Id);
        callprocDate.setParameter(2, team_Id);
        callprocDate.setParameter(3, season_startDate);
        callprocDate.setParameter(4, current_date);
//        ResultSet results = callproc.executeWithResults();
//        callproc.cleanup();
//
//        return leagueId;
    }

    public boolean checkIfLeagueAlreadyExists(String leagueName) throws Exception {
        boolean isexist=false;

        CallStoredProcedure callproc = new CallStoredProcedure("checkIfLeagueAlreadyExists(?,?)");
        callproc.setParameter(1, leagueName);
        callproc.registerOutputParameterBoolean(2);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {

                isexist = results.getBoolean("league_exists");
            }
        }
        else {
            throw new Exception("Error executing check on league");
        }

        callproc.cleanup();

        return isexist;
    }

}
