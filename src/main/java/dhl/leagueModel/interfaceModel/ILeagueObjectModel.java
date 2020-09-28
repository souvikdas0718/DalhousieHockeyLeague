package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface ILeagueObjectModel {

    public String getLeagueName();
    public void setLeagueName(String leagueName);

    public void setTeamPlayerMapping(IParserOutput parsedOutput);
    public HashMap<String, ArrayList<IPlayer>> getTeamPlayerMapping();

    public void setDivisionTeamsMapping(IParserOutput parsedOutput);
    public  HashMap<String, ArrayList<ITeam>> getDivisionTeamsMapping();

    public void setConferenceDivisionsMapping(IParserOutput parsedOutput);
    public  HashMap<String, ArrayList<IDivision>> getConferenceDivisionsMapping();

    public void setLeagueConferencesMapping(IParserOutput parsedOutput);
    public  HashMap<String,ArrayList<IConference>> getLeagueConferenceMapping();

    public ArrayList<IPlayer> getFreeAgents();
    public void setFreeAgents(IParserOutput parsedOutput);

}
