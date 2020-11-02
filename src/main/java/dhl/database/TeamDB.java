package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ICoachDB;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.database.interfaceDB.ITeamDB;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void updateTeam(ITeam team, String divisionName, String leagueName)  throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("updateTeam(?,?,?,?)");
            callproc.setParameter(1, team.getTeamName());
            callproc.setParameter(2, team.getGeneralManager());
            callproc.setParameter(3, divisionName);
            callproc.setParameter(4, leagueName);
            callproc.execute();
            callproc.cleanup();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    public List<ITeam> getTeamList(int divisionId, int leagueId ,DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception {

        ICoachDB iCoachDB = databaseObjectCreationDB.getiCoachDB();
        IPlayerDB iPlayerDB = databaseObjectCreationDB.getiPlayerDB();
        List<ITeam> teamList = new ArrayList<>();

        CallStoredProcedure callTeamProc = new CallStoredProcedure("loadTeams(?,?)");
        callTeamProc.setParameter(1, divisionId);
        callTeamProc.setParameter(2, leagueId);

        ResultSet teamResultSet = callTeamProc.executeWithResults();

        if (teamResultSet==null){
            throw new Exception("Error loading teams");
        }

        while(teamResultSet.next()){
            ITeam team = new Team(teamResultSet.getString("teamName"),
                    teamResultSet.getString("generalManager"),iCoachDB.getTeamCoach(teamResultSet.getInt("teamId"),leagueId,databaseObjectCreationDB),
                    iPlayerDB.getPlayerList(teamResultSet.getInt("teamId"),leagueId));
            teamList.add(team);
        }
        callTeamProc.cleanup();

        return teamList;
    }
}
