package dhl.leagueModel.interfaceModel;

import dhl.database.ILeagueObjectModelData;

import java.util.ArrayList;

public interface ILeagueObjectModel {

    public String getLeagueName();

    public void setLeagueName(String leagueName);

    public ArrayList<IConference> getConferences();

    public void setConferences(ArrayList<IConference> conferences);

    public ArrayList<IPlayer> getFreeAgents();

    public void setFreeAgents(ArrayList<IPlayer> freeAgents);

    public boolean checkIfLeagueModelValid(IValidation validation) throws Exception;

    public ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelData leagueDatabase, String leagueName, String conferenceName, String divisionName,  ITeam newlyCreatedTeam) throws Exception;

    public ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception;

}
