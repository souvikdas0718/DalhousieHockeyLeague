package dhl;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MockParserOutput implements IParserOutput {
    ArrayList<IPlayer> players = new ArrayList<>();
    ArrayList<ITeam> teams = new ArrayList<>();
    ArrayList<IDivision> divisions = new ArrayList<>();
    ArrayList<IConference> conferences = new ArrayList<>();
    ArrayList<IPlayer> freeAgents =new ArrayList<>();

    public ArrayList<IPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<IPlayer> players) {
        this.players=players;
    }

    public ArrayList<ITeam> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<ITeam> teams) {
        this.teams = teams;
    }

    public ArrayList<IDivision> getDivisions() {
        return divisions;
    }

    public void setDivisions(ArrayList<IDivision> divisions) {
        this.divisions=divisions;
    }

    public ArrayList<IConference> getConferences() {
        return conferences;
    }

    public void setConferences(ArrayList<IConference> conferences) {
        this.conferences=conferences;
    }


    public ArrayList<IPlayer> getFreeAgents() {
        return this.freeAgents;
    }

    public void setFreeAgents(ArrayList<IPlayer> freeAgents) {
        this.freeAgents=freeAgents;
    }
}
