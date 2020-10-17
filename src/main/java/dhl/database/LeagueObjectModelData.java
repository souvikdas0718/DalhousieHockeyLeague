package dhl.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Connection;
import java.util.ArrayList;

import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.Division;
import dhl.leagueModel.Conference;
import dhl.database.DatabaseConfigSetup.DatabaseInitialize;
import dhl.database.*;

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

        int leagueId = ileagueDB.insertLeague(leagueModelObj.getLeagueName());

        leagueModelObj.getConferences().forEach((conference)-> {

            ArrayList<IDivision> arrDiv = conference.getDivisions();
            int finalConferenceId = 0;
            try {
                finalConferenceId = iConferenceDB.insertConference(conference.getConferenceName(), leagueId);
            }
            catch(Exception eConference){
                throw new RuntimeException(eConference);
            }

            int finalConferenceId1 = finalConferenceId;
            arrDiv.forEach((division)->{

                ArrayList<ITeam> arrTeam = division.getTeams();
                int finalDivisionId = 0;
                try {
                    finalDivisionId = iDivisionDB.insertDivision(division.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception eDivision) {
                    throw new RuntimeException(eDivision);
                }

                int finalDivisionId1 = finalDivisionId;
                arrTeam.forEach((team)->{

                    ArrayList<IPlayer> arrPlayer = team.getPlayers();
                    int finalTeamId = 0;
                    try {
                        finalTeamId = iTeamDB.insertTeam(team.getTeamName(), team.getGeneralManager(), team.getHeadCoach(), finalDivisionId1, leagueId);
                    } catch (Exception eTeam) {
                        throw new RuntimeException(eTeam);
                    }

                    int finalTeamId1 = finalTeamId;
                    arrPlayer.forEach((player)->{
                        try {
                            iPlayerDB.insertPlayer(player.getPlayerName(),player.getPosition(),player.isCaptainValueBoolean(),
                                    false, finalTeamId1,leagueId);
                        } catch (Exception ePlayer) {
                            throw new RuntimeException(ePlayer);
                        }
                    });
                });
            });
        });

        leagueModelObj.getFreeAgents().forEach((freeAgent) -> {
            try {
                iFreeAgentDB.insertFreeAgent(freeAgent.getPlayerName(),freeAgent.getPosition(),leagueId);
            } catch (Exception eFreeAgent) {
                throw new RuntimeException(eFreeAgent);
            }
        });
    }

    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) throws Exception {

        dhl.leagueModel.LeagueObjectModel obj = new dhl.leagueModel.LeagueObjectModel();

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call loadLeagueModel(?,?)}");
            callproc.setString(1, leagueName);
            callproc.setString(2, teamName);
            callproc.execute();

            ResultSet rs = callproc.getResultSet();

            if (rs==null){
                throw new Exception("Error loading data");
            }
            else {

                ArrayList<IPlayer> playerarr = new ArrayList<>();

                    while (rs.next()) {
                        IPlayer player = new Player(rs.getString("playerName"),rs.getString("position"),rs.getBoolean("isCaptain"));
                        playerarr.add(player);
                    }


                ArrayList<ITeam> teamarr = new ArrayList<>();
                if (callproc.getMoreResults()) {
                    rs = callproc.getResultSet();
                    while (rs.next()) {
                        ITeam team = new Team(teamName,
                                rs.getString("generalManager"),rs.getString("headCoach"),playerarr);
                        teamarr.add(team);
                    }
                }

                ArrayList<IDivision> divisionarr = new ArrayList<>();
                if (callproc.getMoreResults()) {
                    rs = callproc.getResultSet();
                    while (rs.next()) {
                        IDivision division = new Division(rs.getString("divisionName"),teamarr);
                        divisionarr.add(division);
                    }
                }

                ArrayList<IConference> conferencearr = new ArrayList<>();
                if (callproc.getMoreResults()) {
                    rs = callproc.getResultSet();
                    while (rs.next()) {
                        IConference conference = new Conference(rs.getString("conferenceName"),divisionarr);
                        conferencearr.add(conference);
                    }
                }

                obj.leagueName = leagueName;
                obj.conferences = conferencearr;
                obj.freeAgents = new ArrayList<IFreeAgent>();
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}
