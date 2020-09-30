package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface IParserOutput {
    public HashMap<String, ArrayList<IPlayer>> getTeamPlayers();
    public HashMap<String, ArrayList<ITeam>> getDivisionTeams();
    public HashMap<String, ArrayList<IDivision>> getConferenceDivisions();
    public HashMap<String,ArrayList<IConference>> getLeagueConferences();
    public ArrayList<IPlayer> getFreeAgents();

}
