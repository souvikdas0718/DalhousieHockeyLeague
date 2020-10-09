package dhl.leagueModelData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Connection;
import java.util.ArrayList;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.Division;
import dhl.leagueModel.Conference;
import dhl.database.DatabaseInitialize;

public class LeagueObjectModelData implements ILeagueObjectModelData {

    Connection connection;

    public LeagueObjectModelData(){
        DatabaseInitialize databaseInitialize = new DatabaseInitialize();
        connection = databaseInitialize.getConnection();
    }

    public void insertLeagueModel(ILeagueObjectModel leagueModelObj) throws Exception {
        int leagueId = insertLeague(leagueModelObj.getLeagueName());

        leagueModelObj.getConferences().forEach((conference)-> {

            ArrayList<IDivision> arrDiv = conference.getDivisions();
            int finalConferenceId = 0;
            try {
                finalConferenceId = insertConference(conference.getConferenceName(), leagueId);
            }
            catch(Exception eConference){
                throw new RuntimeException(eConference);
            }

            int finalConferenceId1 = finalConferenceId;
            arrDiv.forEach((division)->{

                ArrayList<ITeam> arrTeam = division.getTeams();
                int finalDivisionId = 0;
                try {
                    finalDivisionId = insertDivision(division.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception eDivision) {
                    throw new RuntimeException(eDivision);
                }

                int finalDivisionId1 = finalDivisionId;
                arrTeam.forEach((team)->{

                    ArrayList<IPlayer> arrPlayer = team.getPlayers();
                    int finalTeamId = 0;
                    try {
                        finalTeamId = insertTeam(team.getTeamName(), team.getGeneralManager(), team.getHeadCoach(), finalDivisionId1, leagueId);
                    } catch (Exception eTeam) {
                        throw new RuntimeException(eTeam);
                    }

                    int finalTeamId1 = finalTeamId;
                    arrPlayer.forEach((player)->{
                        try {
                            insertPlayer(player.getPlayerName(),player.getPosition(),player.isCaptainValueBoolean(),
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
                insertPlayer(freeAgent.getPlayerName(),freeAgent.getPosition(),freeAgent.isCaptainValueBoolean(), true, 0,leagueId);
            } catch (Exception eFreeAgent) {
                throw new RuntimeException(eFreeAgent);
            }
        });
    }

    private int insertLeague(String leagueName) throws Exception {
        int leagueId=0;

            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call insertLeague(?,?)}");
            callproc.setString(1, leagueName);
            callproc.registerOutParameter(2, java.sql.Types.INTEGER);
            Boolean hasResult = callproc.execute();

            if(hasResult){
                leagueId = callproc.getInt(2);
            }
            else {
                throw new Exception("League already exists.");
            }

            callproc.close();

        return leagueId;
    }

    private int insertConference(String conferenceName, int leagudId) throws Exception{
        int conferenceId=0;

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call insertConference(?,?,?)}");
            callproc.setInt(1, leagudId);
            callproc.setString(2, conferenceName);
            callproc.registerOutParameter(3, java.sql.Types.INTEGER);

            Boolean hasResult = callproc.execute();
            if(hasResult){
                conferenceId = callproc.getInt(3);
            }
            else {
                throw new Exception("Conference not inserted properly");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conferenceId;
    }

    private int insertDivision(String divisionName, int conferenceId,int leagueId) throws Exception {
        int divisionId=0;

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call insertDivision(?,?,?,?)}");
            callproc.setInt(1, conferenceId);
            callproc.setString(2, divisionName);
            callproc.setInt(3, leagueId);
            callproc.registerOutParameter(4, java.sql.Types.INTEGER);

            Boolean hasResult = callproc.execute();
            if(hasResult){
                divisionId = callproc.getInt(4);
            }
            else {
                throw new Exception("Division not inserted properly");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionId;
    }

    private int insertTeam(String teamName, String generalManager, String headCoach, int divisionId,int leagueId)  throws Exception {
        int teamId = 0;

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call insertTeam(?,?,?,?,?,?)}");
            callproc.setInt(1, divisionId);
            callproc.setString(2, teamName);
            callproc.setString(3, generalManager);
            callproc.setString(4, headCoach);
            callproc.setInt(5, leagueId);
            callproc.registerOutParameter(6, java.sql.Types.INTEGER);

            Boolean hasResult = callproc.execute();
            if(hasResult) {
                teamId = callproc.getInt(6);
            }
            else {
                throw new Exception("Team not inserted properly");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teamId;
    }

    private int insertPlayer(String playerName, String playerPosition, boolean isCaptain, boolean isFreeAgent, int teamId, int leagueId )  throws Exception {
        int playerId =0;

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call insertPlayer(?,?,?,?,?,?,?)}");
            callproc.setString(1, playerName);
            callproc.setString(2, playerPosition);
            callproc.setBoolean(3, isCaptain);
            callproc.setBoolean(4, isFreeAgent);
            callproc.setInt(5, teamId);
            callproc.setInt(6, leagueId);
            callproc.registerOutParameter(7, java.sql.Types.INTEGER);

            Boolean hasResult = callproc.execute();
            if(hasResult){
                playerId = callproc.getInt(7);
            }
            else {
                throw new Exception("Player not inserted properly");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return playerId;
    }

    public boolean checkIfLeagueAlreadyExists(String leagueName) {
        boolean isexist=false;
        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call checkIfLeagueAlreadyExists(?,?)}");
            callproc.setString(1, leagueName);
            callproc.registerOutParameter(2, Types.BOOLEAN);
            Boolean hasResult = callproc.execute();

            if(hasResult){
                isexist = callproc.getBoolean(2);
            }
            else {
                throw new Exception("Error executing check on league");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isexist;
    }

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) {
        boolean isexist=false;

        try {
            CallableStatement callproc = null;
            callproc = connection.prepareCall("{call checkIfTeamAlreadyExists(?,?,?)}");
            callproc.setString(1, teamName);
            callproc.setString(2, divisionName);
            callproc.registerOutParameter(3, Types.BOOLEAN);

            Boolean hasResult = callproc.execute();
            if(hasResult){
                isexist = callproc.getBoolean(3);
            }
            else {
                throw new Exception("Error executing check on team");
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isexist;
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
                obj.freeAgents = new ArrayList<IPlayer>();
            }

            callproc.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}
