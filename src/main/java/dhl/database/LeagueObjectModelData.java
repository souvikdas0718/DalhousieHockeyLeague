package dhl.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.*;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.DatabaseConfigSetup.DatabaseInitialize;

public class LeagueObjectModelData implements ILeagueObjectModelData {

    Connection connection;

    public LeagueObjectModelData() throws Exception {
        DatabaseInitialize databaseInitialize = new DatabaseInitialize();
        connection = databaseInitialize.getConnection();
    }

    public void insertLeagueModel(ILeagueObjectModel leagueModelObj) throws Exception {
        ILeagueDB ileagueDB = new LeagueDB();
        IConferenceDB iConferenceDB = new ConferenceDB();
        IDivisionDB iDivisionDB = new DivisionDB();
        ITeamDB iTeamDB = new TeamDB();
        IPlayerDB iPlayerDB = new PlayerDB();
        IFreeAgentDB iFreeAgentDB = new FreeAgentDB();
        ICoachDB iCoachDB=new CoachDB();

        int leagueId = ileagueDB.insertLeague(leagueModelObj.getLeagueName());

        leagueModelObj.getConferences().forEach((conference)-> {
            ArrayList<IDivision> arrDiv = conference.getDivisions();
            int finalConferenceId = 0;
            try {
                finalConferenceId = iConferenceDB.insertConference(conference.getConferenceName(), leagueId);
            }
            catch(Exception eConference){
                throw new RuntimeException("Error inserting Conference:"+conference.getConferenceName());
            }

            int finalConferenceId1 = finalConferenceId;
            arrDiv.forEach((division)->{

                ArrayList<ITeam> arrTeam = division.getTeams();
                int finalDivisionId = 0;
                try {
                    finalDivisionId = iDivisionDB.insertDivision(division.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception eDivision) {
                    throw new RuntimeException("Error inserting Division:"+division.getDivisionName());
                }

                int finalDivisionId1 = finalDivisionId;
                arrTeam.forEach((team)->{

                    ArrayList<IPlayer> arrPlayer = team.getPlayers();
                    int finalTeamId = 0;
                    try {
                        finalTeamId = iTeamDB.insertTeam(team, finalDivisionId1, leagueId);
                    } catch (Exception eTeam) {
                        throw new RuntimeException("Error inserting Team:"+team.getTeamName());
                    }

                    int finalCoachTeamId = finalTeamId;
                    try {
                        iCoachDB.insertCoach(team.getHeadCoach(), finalCoachTeamId,leagueId);
                    } catch (Exception eCoach) {
                        throw new RuntimeException("Error inserting Coach:"+team.getHeadCoach().getCoachName());
                    }
                    int finalTeamId1 = finalTeamId;
                    arrPlayer.forEach((player)->{
                        try {
                            iPlayerDB.insertPlayer(player, finalTeamId1,leagueId);
                        } catch (Exception ePlayer) {
                            throw new RuntimeException("Error inserting Player:"+player.getPlayerName());
                        }
                    });
                });
            });
        });

        leagueModelObj.getFreeAgents().forEach((freeAgent) -> {
            try {
                iFreeAgentDB.insertFreeAgent(freeAgent,leagueId);
            } catch (Exception eFreeAgent) {
                throw new RuntimeException("Error inserting Free agent:"+ freeAgent.getPlayerName());
            }
        });
        leagueModelObj.getCoaches().forEach((coach) -> {
            try {
                iCoachDB.insertUnassignedCoach(coach,leagueId);
            } catch (Exception eCoach) {
                throw new RuntimeException("Error inserting coach from coach List"+coach.getCoachName());
            }
        });
    }

    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) throws Exception {

        ILeagueObjectModel leagueObjectModel=null;
        int leagueId=0;

        CallStoredProcedure callproc = new CallStoredProcedure("loadLeagueId(?)");
        callproc.setParameter(1, leagueName);
        ResultSet leagueIdResult = callproc.executeWithResults();

        if (null != leagueIdResult) {
            while(leagueIdResult.next()) {
                leagueId = leagueIdResult.getInt("leagueId");
                try {
                    leagueObjectModel=new LeagueObjectModel(leagueName,getConferenceList(leagueId),getFreeAgentList(leagueId));

                }
                catch(Exception exception){
                    throw new Exception("Error occurred while loading league");

                }
            }
        }
        else {
            throw new Exception("League not found");
        }

        callproc.cleanup();
       return leagueObjectModel;
    }

    public ArrayList<IConference> getConferenceList(int leagueId) throws Exception {
        ArrayList<IConference> conferencesList = new ArrayList<>();
        CallStoredProcedure callConfProc = new CallStoredProcedure("loadConferences(?)");
        callConfProc.setParameter(1, leagueId);
        ResultSet conferencesResultSet = callConfProc.executeWithResults();

        if (conferencesResultSet==null){
            throw new Exception("Error loading conferences");
        }

        while(conferencesResultSet.next()){
            IConference conference = new Conference(conferencesResultSet.getString("conferenceName"),
                    getDivisionList(conferencesResultSet.getInt("conferenceId"),leagueId));
            conferencesList.add(conference);
        }
        callConfProc.cleanup();
        return conferencesList;
    }

    public ArrayList<IDivision> getDivisionList(int conferenceId, int leagueId) throws Exception{
        ArrayList<IDivision> divisionList = new ArrayList<>();
        CallStoredProcedure callDivisionProc = new CallStoredProcedure("loadDivisions(?,?)");
        callDivisionProc.setParameter(1, conferenceId);
        callDivisionProc.setParameter(2, leagueId);
        ResultSet divisionsResultSet = callDivisionProc.executeWithResults();

        if (divisionsResultSet==null){
            throw new Exception("Error loading divisions");
        }

        while(divisionsResultSet.next()){
            IDivision division = new Division(divisionsResultSet.getString("divisionName"),
                    getTeamList(divisionsResultSet.getInt("divisionId"),leagueId));
            divisionList.add(division);
        }
        callDivisionProc.cleanup();
        return divisionList;
    }

    public ArrayList<ITeam> getTeamList(int divisionId,int leagueId ) throws Exception {
        ArrayList<ITeam> teamList = new ArrayList<>();
        CallStoredProcedure callTeamProc = new CallStoredProcedure("loadTeams(?,?)");
        callTeamProc.setParameter(1, divisionId);
        callTeamProc.setParameter(2, leagueId);
        ResultSet teamResultSet = callTeamProc.executeWithResults();

        if (teamResultSet==null){
            throw new Exception("Error loading teams");
        }

        while(teamResultSet.next()){
            ITeam team = new Team(teamResultSet.getString("teamName"),
                    teamResultSet.getString("generalManager"),getTeamCoach(teamResultSet.getInt("teamId"),leagueId),
                    getPlayerList(teamResultSet.getInt("teamId"),leagueId));
            teamList.add(team);
        }
        callTeamProc.cleanup();
        return teamList;
    }

    public ICoach getTeamCoach(int teamId,int leagueId) throws Exception {
        ICoach headCoach=null;
        CallStoredProcedure callCoachProc = new CallStoredProcedure("loadCoach(?,?)");
        callCoachProc.setParameter(1, teamId);
        callCoachProc.setParameter(2, leagueId);
        ResultSet coachResultSet = callCoachProc.executeWithResults();

        if (coachResultSet==null){
            throw new Exception("Error loading coach");
        }

        while(coachResultSet.next()){
            headCoach=new Coach(coachResultSet.getString("coachName"),coachResultSet.getInt("skating"),
                    coachResultSet.getInt("shooting"),coachResultSet.getInt("checking"),coachResultSet.getInt("saving"));
        }
        callCoachProc.cleanup();
        return headCoach;
    }

    public ArrayList<IPlayer> getPlayerList(int teamId,int leagueId) throws Exception {
        ArrayList<IPlayer> playerList = new ArrayList<>();
        CallStoredProcedure callPlayerProc = new CallStoredProcedure("loadPlayers(?,?)");
        callPlayerProc.setParameter(1, teamId);
        callPlayerProc.setParameter(2, leagueId);
        ResultSet playerResultSet = callPlayerProc.executeWithResults();

        if (playerResultSet==null){
            throw new Exception("Error loading players");
        }

        while(playerResultSet.next()){
            IPlayerStatistics playerStatistics=new PlayerStatistics(playerResultSet.getInt("age"), playerResultSet.getInt("skating"),
                    playerResultSet.getInt("shooting"),playerResultSet.getInt("checking"),playerResultSet.getInt("saving"));
            IPlayer player = new Player(playerResultSet.getString("playerName"),playerResultSet.getString("position"),playerResultSet.getBoolean("isCaptain"),playerStatistics);
            playerList.add(player);

        }
        callPlayerProc.cleanup();
        return playerList;
    }

    public ArrayList<IFreeAgent> getFreeAgentList(int leagueId) throws Exception {
        ArrayList<IFreeAgent> freeAgentList = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadFreeAgents(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet agentsResultSet = callAgentProc.executeWithResults();

        if (agentsResultSet==null){
            throw new Exception("Error loading Free agents");
        }

        while (agentsResultSet.next()) {
            IPlayerStatistics playerStatistics=new PlayerStatistics(agentsResultSet.getInt("age"),agentsResultSet.getInt("skating"),
                    agentsResultSet.getInt("shooting"),agentsResultSet.getInt("checking"),agentsResultSet.getInt("saving"));
            IFreeAgent freeAgent = new FreeAgent(agentsResultSet.getString("playerName"),agentsResultSet.getString("playerPosition"),playerStatistics);
            freeAgentList.add(freeAgent);
        }
        callAgentProc.cleanup();
       return freeAgentList;
    }

}
