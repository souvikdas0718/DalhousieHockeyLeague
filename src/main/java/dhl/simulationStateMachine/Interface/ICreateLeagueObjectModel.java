package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface ICreateLeagueObjectModel {

    public LeagueObjectModel getLeagueObjectModel() throws Exception;
    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray) throws Exception;
    public ArrayList<IDivision> getDivisionObjectArrayList(JSONArray divisionJsonArray) throws Exception;
    public ArrayList<ITeam> getTeamObjectArrayList(JSONArray TeamJsonArray) throws Exception;
    public ArrayList<IPlayer> getPlayerArrayList(JSONArray PlayerJsonArray) throws Exception;
}
