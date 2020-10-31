package dhl.leagueModel.interfaceModel;

import dhl.database.interfaceDB.ILeagueObjectModelData;

import java.util.List;

public interface ILeagueObjectModel {

    String getLeagueName();

    List<IConference> getConferences();

    List<IPlayer> getFreeAgents();

    boolean checkIfLeagueModelValid(IValidation validation,ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception;

    ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelData leagueDatabase, ILeagueObjectModelInput saveLeagueInput) throws Exception;

    ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception;

    List<ICoach> getCoaches() ;

    void setCoaches(List<ICoach> coaches);

    List getManagers();

    void setManagers(List managers);

}
