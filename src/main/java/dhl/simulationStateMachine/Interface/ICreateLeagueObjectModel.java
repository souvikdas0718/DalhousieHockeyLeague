package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface ICreateLeagueObjectModel {

    public LeagueObjectModel getLeagueObjectModel();
    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray);
    public ArrayList<IDivision> getDivisionObjectArrayList(JSONArray divisionJsonArray);
    public ArrayList<ITeam> getTeamObjectArrayList(JSONArray TeamJsonArray);
    public ArrayList<IPlayer> getPlayerArrayList(JSONArray PlayerJsonArray);
}
