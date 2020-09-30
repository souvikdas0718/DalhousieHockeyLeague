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
        teamPlayersMapping=new HashMap<>();
        divisionTeamsMapping = new HashMap<>();
        conferenceDivisionMapping=new HashMap<>();
        leagueConferencesMapping=new HashMap<>();
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setTeamPlayerMapping(IParserOutput parsedOutput,String teamName){
        this.teamPlayersMapping.put(teamName,parsedOutput.getPlayers());
    }
    public  HashMap<String, ArrayList<IPlayer>> getTeamPlayerMapping(){
        return teamPlayersMapping;
    }

    public void setDivisionTeamsMapping(IParserOutput parsedOutput,String divisionName){
        this.divisionTeamsMapping.put(divisionName,parsedOutput.getTeams());

    }
    public  HashMap<String, ArrayList<ITeam>> getDivisionTeamsMapping(){
        return divisionTeamsMapping;
    }

    public void setConferenceDivisionsMapping(IParserOutput parsedOutput,String conferenceName){
        this.conferenceDivisionMapping.put(conferenceName,parsedOutput.getDivisions());

    }
    public  HashMap<String, ArrayList<IDivision>> getConferenceDivisionsMapping(){
        return conferenceDivisionMapping;
    }

    public void setLeagueConferencesMapping(IParserOutput parsedOutput,String leagueName){
        this.leagueConferencesMapping.put(leagueName,parsedOutput.getConferences());

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
