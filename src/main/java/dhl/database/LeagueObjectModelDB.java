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
    DatabaseObjectCreationDB databaseObjectCreationDB;

    public LeagueObjectModelDB() throws Exception {
        DatabaseInitialize databaseInitialize = new DatabaseInitialize();
        connection = databaseInitialize.getConnection();
        databaseObjectCreationDB = new DatabaseObjectCreationDB();
    }

    public void insertLeagueModel(ILeagueObjectModel leagueObjectModel) throws Exception {
        ILeagueDB ileagueDB = databaseObjectCreationDB.getLeagueDB();
        int leagueId = ileagueDB.insertLeague(leagueObjectModel.getLeagueName());

        leagueObjectModel.getConferences().forEach((conference)-> {
            List<IDivision> divisions = conference.getDivisions();
            int conferenceId = 0;
            try {
                IConferenceDB iConferenceDB = databaseObjectCreationDB.getConferenceDB();
                conferenceId = iConferenceDB.insertConference(conference.getConferenceName(), leagueId);
            }
            catch(Exception eConference){
                throw new RuntimeException("Error inserting Conference:"+conference.getConferenceName());
            }

            int finalConferenceId1 = conferenceId;
            divisions.forEach((division)->{

                List<ITeam> teams = division.getTeams();
                int divisionId = 0;
                try {
                    IDivisionDB iDivisionDB = databaseObjectCreationDB.getDivisionDB();
                    divisionId = iDivisionDB.insertDivision(division.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception eDivision) {
                    throw new RuntimeException("Error inserting Division:"+division.getDivisionName());
                }

                int finalDivisionId = divisionId;
                teams.forEach((team)->{

                    List<IPlayer> arrPlayer = team.getPlayers();
                    int teamId = 0;
                    try {
                        ITeamDB iTeamDB = databaseObjectCreationDB.getTeamDB();
                        teamId = iTeamDB.insertTeam(team, finalDivisionId, leagueId);
                    } catch (Exception eTeam) {
                        throw new RuntimeException("Error inserting Team:"+team.getTeamName());
                    }

                    int finalCoachTeamId = teamId;
                    try {
                        ICoachDB iCoachDB = databaseObjectCreationDB.getCoachDB();
                        iCoachDB.insertCoach(team.getHeadCoach(), finalCoachTeamId,leagueId);
                    } catch (Exception eCoach) {
                        throw new RuntimeException("Error inserting Coach:"+team.getHeadCoach().getCoachName());
                    }
                    int finalTeamId = teamId;
                    arrPlayer.forEach((player)->{
                        try {
                            IPlayerDB iPlayerDB = databaseObjectCreationDB.getPlayerDB();
                            iPlayerDB.insertPlayer(player, finalTeamId,leagueId);
                        } catch (Exception ePlayer) {
                            throw new RuntimeException("Error inserting Player:"+player.getPlayerName());
                        }
                    });

                });
            });
        });

        insertFreeAgents(leagueObjectModel,leagueId);
        insertUnassignedCoaches(leagueObjectModel,leagueId);
        insertGeneralManagers(leagueObjectModel, leagueId);
    }

    public void insertFreeAgents(ILeagueObjectModel leagueModelObj,int leagueId){
        leagueModelObj.getFreeAgents().forEach((freeAgent) -> {
            try {
                IFreeAgentDB ifreeAgentDB = databaseObjectCreationDB.getFreeAgentDB();
                ifreeAgentDB.insertFreeAgent(freeAgent,leagueId);
            } catch (Exception eFreeAgent) {
                throw new RuntimeException("Error inserting Free agent:"+ freeAgent.getPlayerName());
            }
        });
    }

    public void insertUnassignedCoaches(ILeagueObjectModel leagueModelObj,int leagueId){
        leagueModelObj.getCoaches().forEach((coach) -> {
            try {
                ICoachDB iCoachDB = databaseObjectCreationDB.getCoachDB();
                iCoachDB.insertUnassignedCoach(coach,leagueId);
            } catch (Exception eCoach) {
                throw new RuntimeException("Error inserting coach from coach List "+coach.getCoachName());
            }
        });

    }

    public void insertGeneralManagers(ILeagueObjectModel leagueModelObj, int leagueId){
        leagueModelObj.getGeneralManagers().forEach((generalManager) -> {
            try {
                IGeneralManagerDB iGeneralManagerDB = databaseObjectCreationDB.getGeneralManagerDB();
                iGeneralManagerDB.insertGeneralManagers(generalManager.getGeneralManagerName(),leagueId);
            } catch (Exception eCoach) {
                throw new RuntimeException("Error inserting General managers from List "+generalManager.getGeneralManagerName());
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
                    IConferenceDB conferenceDB = databaseObjectCreationDB.getConferenceDB();
                    List<IConference> conferenceList = conferenceDB.getConferenceList(leagueId,databaseObjectCreationDB);

                    IFreeAgentDB freeAgentDB = databaseObjectCreationDB.getFreeAgentDB();
                    ICoachDB coachDB = databaseObjectCreationDB.getCoachDB();
                    IGeneralManagerDB generalManagerDB = databaseObjectCreationDB.getGeneralManagerDB();
                    IGameConfigDB gameConfigDB = databaseObjectCreationDB.getGameConfigDB();
                    leagueObjectModel=new LeagueObjectModel(leagueName,conferenceList,
                            freeAgentDB.getFreeAgentList(leagueId),
                            coachDB.getUnassignedCoachList(leagueId),
                            generalManagerDB.getManagersList(leagueId),
                            gameConfigDB.loadGamePlayConfig(leagueName));

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

}
