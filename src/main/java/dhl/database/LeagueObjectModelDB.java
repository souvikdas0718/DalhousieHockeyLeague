package dhl.database;

import java.sql.ResultSet;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.*;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.DatabaseConfigSetup.DatabaseInitialize;
import org.json.simple.JSONObject;

public class LeagueObjectModelDB implements ILeagueObjectModelDB {

    Connection connection;
    ILeagueDB ileagueDB;
    IConferenceDB iConferenceDB;
    IDivisionDB iDivisionDB;
    ITeamDB iTeamDB;
    IPlayerDB iPlayerDB;
    IFreeAgentDB ifreeAgentDB;
    ICoachDB iCoachDB;
    IGeneralManagerDB iGeneralManagerDB;

    public LeagueObjectModelDB() throws Exception {
        DatabaseInitialize databaseInitialize = new DatabaseInitialize();
        connection = databaseInitialize.getConnection();
        ileagueDB = new LeagueDB();
        iConferenceDB = new ConferenceDB();
        iDivisionDB = new DivisionDB();
        iTeamDB = new TeamDB();
        iPlayerDB = new PlayerDB();
        ifreeAgentDB = new FreeAgentDB();
        iCoachDB=new CoachDB();
        iGeneralManagerDB = new GeneralManagerDB();
    }

    public void insertLeagueModel(ILeagueObjectModel leagueModelObj) throws Exception {
        int leagueId = ileagueDB.insertLeague(leagueModelObj.getLeagueName());

        leagueModelObj.getConferences().forEach((conference)-> {
            List<IDivision> arrDiv = conference.getDivisions();
            int finalConferenceId = 0;
            try {
                finalConferenceId = iConferenceDB.insertConference(conference.getConferenceName(), leagueId);
            }
            catch(Exception eConference){
                throw new RuntimeException("Error inserting Conference:"+conference.getConferenceName());
            }

            int finalConferenceId1 = finalConferenceId;
            arrDiv.forEach((division)->{

                List<ITeam> arrTeam = division.getTeams();
                int finalDivisionId = 0;
                try {
                    finalDivisionId = iDivisionDB.insertDivision(division.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception eDivision) {
                    throw new RuntimeException("Error inserting Division:"+division.getDivisionName());
                }

                int finalDivisionId1 = finalDivisionId;
                arrTeam.forEach((team)->{

                    List<IPlayer> arrPlayer = team.getPlayers();
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

        insertFreeAgents(leagueModelObj,leagueId);
        insertUnassignedCoaches(leagueModelObj,leagueId);
        insertGeneralManagers(leagueModelObj, leagueId);
    }
    public void insertFreeAgents(ILeagueObjectModel leagueModelObj,int leagueId){
        leagueModelObj.getFreeAgents().forEach((freeAgent) -> {
            try {
                ifreeAgentDB.insertFreeAgent(freeAgent,leagueId);
            } catch (Exception eFreeAgent) {
                throw new RuntimeException("Error inserting Free agent:"+ freeAgent.getPlayerName());
            }
        });
    }

    public void insertUnassignedCoaches(ILeagueObjectModel leagueModelObj,int leagueId){
        leagueModelObj.getCoaches().forEach((coach) -> {
            try {
                iCoachDB.insertUnassignedCoach(coach,leagueId);
            } catch (Exception eCoach) {
                throw new RuntimeException("Error inserting coach from coach List "+coach.getCoachName());
            }
        });

    }

    public void insertGeneralManagers(ILeagueObjectModel leagueModelObj, int leagueId){
        leagueModelObj.getGeneralManagers().forEach((objgeneralManager) -> {
            try {
                iGeneralManagerDB.insertGeneralManagers(objgeneralManager.getGeneralManagerName(),leagueId);
            } catch (Exception eCoach) {
                throw new RuntimeException("Error inserting General managers from List "+objgeneralManager.getGeneralManagerName());
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
                    leagueObjectModel=new LeagueObjectModel(leagueName,getConferenceList(leagueId),getFreeAgentList(leagueId),getUnassignedCoachList(leagueId),getManagersList(leagueId),getGameConfig(leagueId));

                }
                catch(Exception exception){
                    exception.printStackTrace();
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

    public List<IConference> getConferenceList(int leagueId) throws Exception {
        List<IConference> conferencesList = new ArrayList<>();
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

    public List<IDivision> getDivisionList(int conferenceId, int leagueId) throws Exception{
        List<IDivision> divisionList = new ArrayList<>();
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

    public List<ITeam> getTeamList(int divisionId,int leagueId ) throws Exception {
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

    public List<IPlayer> getPlayerList(int teamId,int leagueId) throws Exception {
        List<IPlayer> playerList = new ArrayList<>();
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
            player.setPlayerInjuredDays(playerResultSet.getInt("numberOfDaysInjured"));
            playerList.add(player);

        }
        callPlayerProc.cleanup();
        return playerList;
    }

    public List<IPlayer> getFreeAgentList(int leagueId) throws Exception {
        List<IPlayer> freeAgentList = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadFreeAgents(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet agentsResultSet = callAgentProc.executeWithResults();

        if (agentsResultSet==null){
            throw new Exception("Error loading Free agents");
        }

        while (agentsResultSet.next()) {
            IPlayerStatistics playerStatistics=new PlayerStatistics(agentsResultSet.getInt("age"),agentsResultSet.getInt("skating"),
                    agentsResultSet.getInt("shooting"),agentsResultSet.getInt("checking"),agentsResultSet.getInt("saving"));
            IPlayer freeAgent = new FreeAgent(agentsResultSet.getString("playerName"),agentsResultSet.getString("playerPosition"),playerStatistics);
            freeAgent.setPlayerInjuredDays(agentsResultSet.getInt("numberOfDaysInjured"));
            freeAgentList.add(freeAgent);
        }
        callAgentProc.cleanup();
       return freeAgentList;
    }

    public List<ICoach> getUnassignedCoachList(int leagueId) throws Exception {
        List<ICoach> coaches = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadUnassignedCoaches(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet coachResultSet = callAgentProc.executeWithResults();

        if (coachResultSet==null){
            throw new Exception("Error loading Coaches List");
        }

        while (coachResultSet.next()) {
            ICoach headCoach=new Coach(coachResultSet.getString("coachName"),coachResultSet.getInt("skating"),
                    coachResultSet.getInt("shooting"),coachResultSet.getInt("checking"),coachResultSet.getInt("saving"));
            coaches.add(headCoach);
        }
        callAgentProc.cleanup();
        return coaches;
    }

    public List<IGeneralManager> getManagersList(int leagueId) throws Exception {
        List<IGeneralManager> managers = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadAllManagers(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet managerResultSet = callAgentProc.executeWithResults();

        if (managerResultSet==null){
            throw new Exception("Error loading Managers List");
        }

        while (managerResultSet.next()) {
            IGeneralManager manager=new GeneralManager(managerResultSet.getString("name"));
            managers.add(manager);
        }
        callAgentProc.cleanup();
        return managers;
    }

    public IGameConfig getGameConfig(int leagueId) throws Exception {
        JSONObject configObject= new JSONObject();
        IGameConfig config = new GameConfig(configObject);

        return config;
    }


}
