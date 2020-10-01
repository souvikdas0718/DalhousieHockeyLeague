package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface ILeagueObjectModel {

    public String getLeagueName();
    public void setLeagueName(String leagueName);

    public void setTeamPlayerMapping(IParserOutput parsedOutput,String teamName);
    public HashMap<String, ArrayList<IPlayer>> getTeamPlayerMapping();

    public void setDivisionTeamsMapping(IParserOutput parsedOutput, String divisionName);
    public  HashMap<String, ArrayList<ITeam>> getDivisionTeamsMapping();

    public void setConferenceDivisionsMapping(IParserOutput parsedOutput,String conferenceName);
    public  HashMap<String, ArrayList<IDivision>> getConferenceDivisionsMapping();

    public void setLeagueConferencesMapping(IParserOutput parsedOutput,String leagueName);
    public  HashMap<String,ArrayList<IConference>> getLeagueConferenceMapping();

    public ArrayList<IPlayer> getFreeAgents();
    public void setFreeAgents(IParserOutput parsedOutput);

}
