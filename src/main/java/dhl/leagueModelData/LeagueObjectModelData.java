package dhl.leagueModelData;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LeagueObjectModelData {

    public LeagueObjectModelData(){
        createconnection();
    }

    Connection con;

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

    public void insertLeagueModel(ILeagueObjectModel obj) {
        int leagueId = insertLeague(obj.getLeagueName());
        obj.getConferences().forEach((a)-> {
            ArrayList<IDivision> arrDiv = a.getDivisions();
            int finalConferenceId = insertConference(a.getConferenceName(), leagueId);

            arrDiv.forEach((b)->{
                ArrayList<ITeam> arrTeam = b.getTeams();
                int finalDivisionId = insertDivision(b.getDivisionName(), finalConferenceId, leagueId);

                arrTeam.forEach((c)->{
                    ArrayList<IPlayer> arrPlayer = c.getPlayers();
                    int finalTeamId = insertTeam(c.getTeamName(), c.getGeneralManager(), c.getHeadCoach(), finalDivisionId, leagueId);

                    arrPlayer.forEach((d)->{
                        insertPlayer(d.getPlayerName(),d.getPosition(),d.isCaptainValueBoolean(), false, finalTeamId,leagueId);
                    });
                });
            });
        });
        obj.getFreeAgents().forEach((e) -> {
            System.out.println(e.getPlayerName());
            insertPlayer(e.getPlayerName(),e.getPosition(),e.isCaptainValueBoolean(), true, 0,leagueId);
        });
    }


    public int insertLeague(String leagueName){
        int leagueId=0;
        try {

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leagueId;
    }
    public int insertConference(String conferenceName, int leagudId){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conferenceId;
    }
    public int insertDivision(String divisionName, int conferenceId,int leagueId){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return divisionId;
    }
    public int insertTeam(String teamName, String generalManager, String headCoach, int divisionId,int leagueId){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teamId;
    }
    public int insertPlayer(String playerName, String playerPosition, boolean isCaptain, boolean isFreeAgent, int teamId, int leagueId ){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerId;
    }

    public boolean checkIfLeagueAlreadyExists(String leagueName){
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
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isexist;
    }

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName){
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
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isexist;
    }
    public ILeagueObjectModel loadLeagueModel(String leagueName, String conferenceName, String divisionName, String teamName){
        dhl.leagueModel.LeagueObjectModel obj = new dhl.leagueModel.LeagueObjectModel();
        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call loadLeagueModel(?,?,?,?)}");
            stmt.setString(1, leagueName);
            stmt.setString(2, conferenceName);
            stmt.setString(3, divisionName);
            stmt.setString(4, teamName);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            ArrayList<IPlayer> playerarr = new ArrayList<>();
            if (rs.next()) {
                rs = stmt.getResultSet();

                while (rs.next()) {
                    IPlayer player = new Player(rs.getString("playerName"),rs.getString("position"),rs.getBoolean("isCaptain"));
                    playerarr.add(player);
                }
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

            IDivision division = new Division(divisionName,teamarr);
            divisionarr.add(division);

            ArrayList<IConference> conferencearr = new ArrayList<>();
            IConference conference = new Conference(conferenceName,divisionarr);
            conferencearr.add(conference);

            stmt.close();

            obj.leagueName = leagueName;
            obj.conferences = conferencearr;
            obj.freeAgents = new ArrayList<IPlayer>();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
