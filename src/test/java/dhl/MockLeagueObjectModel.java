package dhl;

import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MockLeagueObjectModel implements ILeagueObjectModel {

    @Override
    public String getLeagueName() {
        return null;
    }

    @Override
    public void setLeagueName(String leagueName) {

    }

    @Override
    public void setTeamPlayerMapping(IParserOutput parsedOutput, String teamName) {

    }

    @Override
    public HashMap<String, ArrayList<IPlayer>> getTeamPlayerMapping() {
        return null;
    }

    @Override
    public void setDivisionTeamsMapping(IParserOutput parsedOutput, String divisionName) {

    }

    @Override
    public HashMap<String, ArrayList<ITeam>> getDivisionTeamsMapping() {
        return null;
    }

    @Override
    public void setConferenceDivisionsMapping(IParserOutput parsedOutput, String conferenceName) {

    }

    @Override
    public HashMap<String, ArrayList<IDivision>> getConferenceDivisionsMapping() {
        return null;
    }

    @Override
    public void setLeagueConferencesMapping(IParserOutput parsedOutput, String leagueName) {

    }

    @Override
    public HashMap<String, ArrayList<IConference>> getLeagueConferenceMapping() {
        return null;
    }

    @Override
    public ArrayList<IPlayer> getFreeAgents() {
        return null;
    }

    @Override
    public void setFreeAgents(IParserOutput parsedOutput) {

    }
}
