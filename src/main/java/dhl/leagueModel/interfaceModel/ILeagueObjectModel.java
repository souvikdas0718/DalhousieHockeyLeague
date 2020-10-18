package dhl.leagueModel.interfaceModel;

import dhl.database.interfaceDB.ILeagueObjectModelData;

import java.util.ArrayList;

public interface ILeagueObjectModel {

    public String getLeagueName();

    public void setLeagueName(String leagueName);

    public ArrayList<IConference> getConferences();

    public void setConferences(ArrayList<IConference> conferences);

    public ArrayList<IFreeAgent> getFreeAgents();

    public void setFreeAgents(ArrayList<IFreeAgent> freeAgents);

    public boolean checkIfLeagueModelValid(IValidation validation) throws Exception;

    public ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelData leagueDatabase, String leagueName, String conferenceName, String divisionName,  ITeam newlyCreatedTeam) throws Exception;

    public ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception;

    public ArrayList<ICoach> getCoaches() ;

    public void setCoaches(ArrayList<ICoach> coaches);

    public ArrayList getManagers();

    public void setManagers(ArrayList managers);
}
