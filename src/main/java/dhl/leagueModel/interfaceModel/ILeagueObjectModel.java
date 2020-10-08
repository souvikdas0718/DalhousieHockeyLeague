package dhl.leagueModel.interfaceModel;

import dhl.leagueModelData.ILeagueObjectModelData;

import java.util.ArrayList;

public interface ILeagueObjectModel {

    public String getLeagueName();

    public void setLeagueName(String leagueName);

    public ArrayList<IConference> getConferences();

    public void setConferences(ArrayList<IConference> conferences);

    public ArrayList<IPlayer> getFreeAgents();

    public void setFreeAgents(ArrayList<IPlayer> freeAgents);

    public boolean checkIfLeagueModelValid(IValidation validation) throws Exception;

    public void checkIfLeagueHasEvenConferences() throws Exception;

    public ILeagueObjectModel createTeam(ILeagueObjectModelData leagueDatabase, String leagueName, String conferenceName, String divisionName, String teamName,
                                         String generalManager, String headCoach) throws Exception;

    public ILeagueObjectModel loadTeam(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception;

}
