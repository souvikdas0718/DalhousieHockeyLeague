package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface ICreateLeagueObjectModel {

    public LeagueObjectModel getLeagueObjectModel();
    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray);
    public ArrayList getDivisionObjectArrayList(JSONArray divisionJsonArray);
    public ArrayList getTeamObjectArrayList(JSONArray TeamJsonArray);
    public ArrayList getPlayerArrayList(JSONArray PlayerJsonArray);
}
