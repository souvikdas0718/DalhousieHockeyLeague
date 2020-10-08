package dhl.leagueModelData;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeagueObjectModelData implements ILeagueObjectModelData {

    Connection con;

    public LeagueObjectModelData(){
        createconnection();
    }

    void createconnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_2_DEVINT?serverTimezone=UTC","CSCI5308_2_DEVINT_USER","F2qzG5VBxf");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LeagueObjectModelData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LeagueObjectModelData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertLeagueModel(ILeagueObjectModel obj) throws Exception {
        int leagueId = insertLeague(obj.getLeagueName());

        obj.getConferences().forEach((a)-> {

            ArrayList<IDivision> arrDiv = a.getDivisions();
            int finalConferenceId = 0;
            try {
                finalConferenceId = insertConference(a.getConferenceName(), leagueId);
            }
            catch(Exception ex){
                throw new RuntimeException(ex);
            }

            int finalConferenceId1 = finalConferenceId;
            arrDiv.forEach((b)->{

                ArrayList<ITeam> arrTeam = b.getTeams();
                int finalDivisionId = 0;
                try {
                    finalDivisionId = insertDivision(b.getDivisionName(), finalConferenceId1, leagueId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                int finalDivisionId1 = finalDivisionId;
                arrTeam.forEach((c)->{

                    ArrayList<IPlayer> arrPlayer = c.getPlayers();
                    int finalTeamId = 0;
                    try {
                        finalTeamId = insertTeam(c.getTeamName(), c.getGeneralManager(), c.getHeadCoach(), finalDivisionId1, leagueId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    int finalTeamId1 = finalTeamId;
                    arrPlayer.forEach((d)->{
                        try {
                            insertPlayer(d.getPlayerName(),d.getPosition(),d.isCaptainValueBoolean(),
                                    false, finalTeamId1,leagueId);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            });
        });

        obj.getFreeAgents().forEach((e) -> {
            try {
                insertPlayer(e.getPlayerName(),e.getPosition(),e.isCaptainValueBoolean(), true, 0,leagueId);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public int insertLeague(String leagueName) throws Exception {
        int leagueId=0;

            CallableStatement stmt = null;
            stmt = con.prepareCall("{call insertLeague(?,?)}");
            stmt.setString(1, leagueName);
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            Boolean hasResult = stmt.execute();

            if(hasResult){
                leagueId = stmt.getInt(2);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

        return leagueId;
    }

    public int insertConference(String conferenceName, int leagudId) throws Exception{
        int conferenceId=0;

        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call insertConference(?,?,?)}");
            stmt.setInt(1, leagudId);
            stmt.setString(2, conferenceName);
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);

            Boolean hasResult = stmt.execute();
            if(hasResult){
                conferenceId = stmt.getInt(3);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conferenceId;
    }

    public int insertDivision(String divisionName, int conferenceId,int leagueId) throws Exception {
        int divisionId=0;

        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call insertDivision(?,?,?,?)}");
            stmt.setInt(1, conferenceId);
            stmt.setString(2, divisionName);
            stmt.setInt(3, leagueId);
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            Boolean hasResult = stmt.execute();
            if(hasResult){
                divisionId = stmt.getInt(4);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionId;
    }

    public int insertTeam(String teamName, String generalManager, String headCoach, int divisionId,int leagueId)  throws Exception {
        int teamId = 0;

        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call insertTeam(?,?,?,?,?,?)}");
            stmt.setInt(1, divisionId);
            stmt.setString(2, teamName);
            stmt.setString(3, generalManager);
            stmt.setString(4, headCoach);
            stmt.setInt(5, leagueId);
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);

            Boolean hasResult = stmt.execute();
            if(hasResult) {
                teamId = stmt.getInt(6);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teamId;
    }

    public int insertPlayer(String playerName, String playerPosition, boolean isCaptain, boolean isFreeAgent, int teamId, int leagueId )  throws Exception {
        int playerId =0;

        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call insertPlayer(?,?,?,?,?,?,?)}");
            stmt.setString(1, playerName);
            stmt.setString(2, playerPosition);
            stmt.setBoolean(3, isCaptain);
            stmt.setBoolean(4, isFreeAgent);
            stmt.setInt(5, teamId);
            stmt.setInt(6, leagueId);
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);

            Boolean hasResult = stmt.execute();
            if(hasResult){
                playerId = stmt.getInt(7);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return playerId;
    }

    public boolean checkIfLeagueAlreadyExists(String leagueName) {
        boolean isexist=false;
        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call checkIfLeagueAlreadyExists(?,?)}");
            stmt.setString(1, leagueName);
            stmt.registerOutParameter(2, Types.BOOLEAN);
            Boolean hasResult = stmt.execute();

            if(hasResult){
                isexist = stmt.getBoolean(2);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

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
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call checkIfTeamAlreadyExists(?,?,?)}");
            stmt.setString(1, teamName);
            stmt.setString(2, divisionName);
            stmt.registerOutParameter(3, Types.BOOLEAN);

            Boolean hasResult = stmt.execute();
            if(hasResult){
                isexist = stmt.getBoolean(3);
            }
            else {
                throw new Exception("Data not inserted properly");
            }

            stmt.close();

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
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call loadLeagueModel(?,?)}");
            stmt.setString(1, leagueName);
            stmt.setString(2, teamName);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

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
                if (stmt.getMoreResults()) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        ITeam team = new Team(teamName,
                                rs.getString("generalManager"),rs.getString("headCoach"),playerarr);
                        teamarr.add(team);
                    }
                }

                ArrayList<IDivision> divisionarr = new ArrayList<>();
                if (stmt.getMoreResults()) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        IDivision division = new Division(rs.getString("divisionName"),teamarr);
                        divisionarr.add(division);
                    }
                }

                ArrayList<IConference> conferencearr = new ArrayList<>();
                if (stmt.getMoreResults()) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        IConference conference = new Conference(rs.getString("conferenceName"),divisionarr);
                        conferencearr.add(conference);
                    }
                }

                obj.leagueName = leagueName;
                obj.conferences = conferencearr;
                obj.freeAgents = new ArrayList<IPlayer>();
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}
