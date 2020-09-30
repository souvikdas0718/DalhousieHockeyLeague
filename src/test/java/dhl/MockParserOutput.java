package dhl;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MockParserOutput implements IParserOutput {

    public HashMap<String, ArrayList<IPlayer>> getTeamPlayers() {
        ArrayList<IPlayer> playerList = new ArrayList<IPlayer>();
        IPlayer player= new Player();
        player.setPlayerName("Rob");
        player.setCaptain(true);
        player.setTeamName("Boston");
        player.setPosition("forward");
        playerList.add(player);
        IPlayer player2= new Player();
        player2.setPlayerName("Rob");
        player2.setCaptain(false);
        player2.setTeamName("Boston");
        player2.setPosition("forward");
        playerList.add(player2);
        HashMap<String,ArrayList<IPlayer>> teamPlayers=new HashMap<>();
        teamPlayers.put("Boston",playerList);
        return teamPlayers;
    }

    public HashMap<String, ArrayList<ITeam>> getDivisionTeams() {
        ArrayList<ITeam> teamList = new ArrayList<ITeam>();
        ITeam team= new Team();
        team.setGeneralManager("Harry");
        team.setHeadCoach("Mike");
        team.setTeamName("Boston");
        team.setConferenceName("Western");
        team.setDivisionName("Atlantic");
        teamList.add(team);
        HashMap<String,ArrayList<ITeam>> divisionTeams=new HashMap<>();
        divisionTeams.put("Atlantic",teamList);
        return divisionTeams;
    }

    public HashMap<String, ArrayList<IDivision>> getConferenceDivisions() {
        ArrayList<IDivision> divisionList = new ArrayList<IDivision>();
        IDivision division= new Division();
        division.setDivisionName("Atlantic");
        divisionList.add(division);
        HashMap<String,ArrayList<IDivision>> divisionTeams=new HashMap<>();
        divisionTeams.put("Eastern",divisionList);
        return divisionTeams;
    }

    public HashMap<String, ArrayList<IConference>> getLeagueConferences() {
        ArrayList<IConference> conferenceList = new ArrayList<IConference>();
        IConference conference= new Conference();
        conference.setConferenceName("Eastern");
        conference.setLeagueName("Dalhousie Hockey League");
        conferenceList.add(conference);
        HashMap<String,ArrayList<IConference>> leagueConferences=new HashMap<>();
        leagueConferences.put("Dalhousie Hockey League",conferenceList);
        return leagueConferences;
    }

    public ArrayList<IPlayer> getFreeAgents() {
        ArrayList<IPlayer> playerList = new ArrayList<IPlayer>();
        IPlayer player= new Player();
        player.setPlayerName("Job");
        player.setCaptain(false);
        player.setTeamName("Quebec");
        player.setPosition("forward");
        playerList.add(player);
        return playerList;
    }

}
