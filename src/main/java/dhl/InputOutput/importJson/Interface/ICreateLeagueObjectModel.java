package dhl.InputOutput.importJson.Interface;

import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;

public interface ICreateLeagueObjectModel {

    public ILeagueObjectModel getLeagueObjectModel() throws Exception;

    public ArrayList<IConference> getConferenceArrayList() throws Exception;

    public ArrayList<IDivision> getDivisionObjectArrayList() throws Exception;

    public ArrayList<ITeam> getTeamObjectArrayList() throws Exception;

    public ArrayList<IPlayer> getPlayerArrayList() throws Exception;
}