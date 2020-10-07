package dhl.leagueModelData;

import dhl.leagueModel.Conference;
import dhl.leagueModel.interfaceModel.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LeagueObjectModelData implements ILeagueObjectModelData {
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
            System.out.println(a.getConferenceName());
            ArrayList<IDivision> arrDiv = a.getDivisions();
            int finalConferenceId = insertConference(a.getConferenceName(), leagueId);

            arrDiv.forEach((b)->{
                System.out.println(b.getDivisionName());
                ArrayList<ITeam> arrTeam = b.getTeams();
                int finalDivisionId = insertDivision(b.getDivisionName(), finalConferenceId, leagueId);

                arrTeam.forEach((c)->{
                    System.out.println(c.getTeamName());
                    ArrayList<IPlayer> arrPlayer = c.getPlayers();
                    int finalTeamId = insertTeam(c.getTeamName(), c.getGeneralManager(), c.getHeadCoach(), finalDivisionId, leagueId);

                    arrPlayer.forEach((d)->{
                        System.out.println(d.getPlayerName());
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
                System.out.println("Success");
                leagueId = stmt.getInt(2);
                System.out.println("id = " + leagueId);

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
                System.out.println("Success");
                conferenceId = stmt.getInt(3);
                System.out.println("id = " + conferenceId);
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
                System.out.println("Success");
                divisionId = stmt.getInt(4);
                System.out.println("id = " + divisionId);
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
                System.out.println("Success");
                teamId = stmt.getInt(6);
                System.out.println("id = " + teamId);
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
                System.out.println("Success");
                playerId = stmt.getInt(7);
                System.out.println("id = " + playerId);
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
                System.out.println("Success");
                isexist = stmt.getBoolean(2);
                System.out.println("id = " + isexist);
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
                System.out.println("Success");
                isexist = stmt.getBoolean(3);
                System.out.println("id = " + isexist);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isexist;
    }
    public ILeagueObjectModel loadLeagueModel(String leaguename){
        dhl.leagueModel.LeagueObjectModel obj = new dhl.leagueModel.LeagueObjectModel();
        try {
            CallableStatement stmt = null;
            stmt = con.prepareCall("{call loadLeagueModel(?)}");
            stmt.setString(1, leaguename);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                obj.leagueName = rs.getString("leagueName");
            }
            if (stmt.getMoreResults()) {
//                rs = stmt.getResultSet();
//                while (rs.next()) {
//                    IConference conference1=new Conference(rs.getString("conferenceName"));
//                    ArrayList<IConference> conferences= new ArrayList<>();
//                    obj.conferences
//                }
            }
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    System.out.println(rs.getString("divisionName"));
                    //System.out.println(rs.getString("pass"));
                }
            }
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    System.out.println(rs.getString("teamName"));
                    //System.out.println(rs.getString("pass"));
                }
            }
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    System.out.println(rs.getString("playerName"));
                    //System.out.println(rs.getString("pass"));
                }
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
