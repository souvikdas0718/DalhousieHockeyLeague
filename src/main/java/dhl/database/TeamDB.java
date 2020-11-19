package dhl.database;

import dhl.database.databaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ICoachDB;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.database.interfaceDB.ITeamDB;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDB implements ITeamDB {
    public int insertTeam(ITeam team, int divisionId, int leagueId) throws Exception {
        int teamId = 0;

        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertTeam(?,?,?,?)");
            callproc.setParameter(1, divisionId);
            callproc.setParameter(2, team.getTeamName());
            callproc.setParameter(3, team.getGeneralManager());
            callproc.setParameter(4, leagueId);
            ResultSet results = callproc.executeWithResults();

            if (null == results) {
                throw new Exception("Team not inserted properly");
            }
            while (results.next()) {

                teamId = results.getInt("team_Id");
            }

            callproc.cleanup();

        } catch (SQLException exception) {
            throw new Exception("Team not inserted properly");
        }

        return teamId;
    }

    public void updateTeam(ITeam team, String divisionName, String leagueName) throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("updateTeam(?,?,?,?)");
            callproc.setParameter(1, team.getTeamName());
            callproc.setParameter(2, team.getGeneralManager());
            callproc.setParameter(3, leagueName);
            callproc.setParameter(4, divisionName);
            callproc.executeWithResults();
            callproc.cleanup();

        } catch (SQLException exception) {
            throw new Exception("Update team failed");
        }
    }

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception {
        boolean isexist = false;

        CallStoredProcedure callproc = new CallStoredProcedure("checkIfTeamAlreadyExists(?,?,?)");
        callproc.setParameter(1, teamName);
        callproc.setParameter(2, divisionName);
        callproc.registerOutputParameterBoolean(3);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Error executing check on team");

        }
        while (results.next()) {
            isexist = results.getBoolean("team_Exists");
        }

        callproc.cleanup();

        return isexist;
    }

    public List<ITeam> getTeamList(int divisionId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception {

        ICoachDB coachDB = databaseObjectCreationDB.getCoachDB();
        IPlayerDB playerDB = databaseObjectCreationDB.getPlayerDB();
        List<ITeam> teamList = new ArrayList<>();

        CallStoredProcedure callTeamProc = new CallStoredProcedure("loadTeams(?,?)");
        callTeamProc.setParameter(1, divisionId);
        callTeamProc.setParameter(2, leagueId);

        ResultSet teamResultSet = callTeamProc.executeWithResults();

        if (teamResultSet == null) {
            throw new Exception("Error loading teams");
        }

        while (teamResultSet.next()) {
            ITeam team = new Team(teamResultSet.getString("teamName"),
                    teamResultSet.getString("generalManager"), coachDB.getTeamCoach(teamResultSet.getInt("teamId"), leagueId, databaseObjectCreationDB),
                    playerDB.getPlayerList(teamResultSet.getInt("teamId"), leagueId));
            teamList.add(team);
        }
        callTeamProc.cleanup();

        return teamList;
    }
}
