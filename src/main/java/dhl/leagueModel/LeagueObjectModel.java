package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public HashMap<String,ArrayList<IPlayer>>teamPlayersMapping;
    public HashMap<String,ArrayList<ITeam>>divisionTeamsMapping;
    public HashMap<String, ArrayList<IDivision>>conferenceDivisionMapping;
    public HashMap<String,ArrayList<IConference>>leagueConferencesMapping;
    public ArrayList<IPlayer>freeAgents;

    public LeagueObjectModel(){
        leagueName="";
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setTeamPlayerMapping(IParserOutput parsedOutput){
        this.teamPlayersMapping = parsedOutput.getTeamPlayers();

    }
    public  HashMap<String, ArrayList<IPlayer>> getTeamPlayerMapping(){
        return teamPlayersMapping;
    }

    public void setDivisionTeamsMapping(IParserOutput parsedOutput){
        this.divisionTeamsMapping = parsedOutput.getDivisionTeams();

    }
    public  HashMap<String, ArrayList<ITeam>> getDivisionTeamsMapping(){
        return divisionTeamsMapping;
    }

    public void setConferenceDivisionsMapping(IParserOutput parsedOutput){
        this.conferenceDivisionMapping = parsedOutput.getConferenceDivisions();

    }
    public  HashMap<String, ArrayList<IDivision>> getConferenceDivisionsMapping(){
        return conferenceDivisionMapping;
    }

    public void setLeagueConferencesMapping(IParserOutput parsedOutput){
        this.leagueConferencesMapping = parsedOutput.getLeagueConferences();

    }
    public  HashMap<String,ArrayList<IConference>> getLeagueConferenceMapping(){
        return leagueConferencesMapping;
    }


    public ArrayList<IPlayer> getFreeAgents() {
        return freeAgents;
    }

    public void setFreeAgents(IParserOutput parsedOutput) {
        this.freeAgents = parsedOutput.getFreeAgents();
    }

}
