package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ITeamDB;
import dhl.leagueModel.interfaceModel.ITeam;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDB implements ITeamDB {
    public int insertTeam(ITeam team, int divisionId, int leagueId)  throws Exception {
        int teamId = 0;

        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertTeam(?,?,?,?)");
            callproc.setParameter(1, divisionId);
            callproc.setParameter(2, team.getTeamName());
            callproc.setParameter(3, team.getGeneralManager());
            callproc.setParameter(4, leagueId);
            ResultSet results = callproc.executeWithResults();

            if (null != results) {
                while (results.next()) {

                    teamId = results.getInt("team_Id");
                }
            }
            else {
                throw new Exception("Team not inserted properly");
            }

            callproc.cleanup();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teamId;
    }

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception {
        boolean isexist=false;

        CallStoredProcedure callproc = new CallStoredProcedure("checkIfTeamAlreadyExists(?,?,?)");
        callproc.setParameter(1, teamName);
        callproc.setParameter(2, divisionName);
        callproc.registerOutputParameterBoolean(3);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                isexist = results.getBoolean("team_Exists");
            }
        }
        else {
            throw new Exception("Error executing check on team");
        }

        callproc.cleanup();

        return isexist;
    }

}
