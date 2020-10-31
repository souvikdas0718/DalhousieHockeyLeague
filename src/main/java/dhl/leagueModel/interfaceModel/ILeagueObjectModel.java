package dhl.leagueModel.interfaceModel;

import dhl.database.interfaceDB.ILeagueObjectModelDB;

import java.util.List;

public interface ILeagueObjectModel {

    String getLeagueName();

    List<IConference> getConferences();

    List<IPlayer> getFreeAgents();

    boolean checkIfLeagueModelValid(IValidation validation,ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception;

    ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, ILeagueObjectModelInput saveLeagueInput) throws Exception;

    ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, String leagueName, String teamName) throws Exception;

    List<ICoach> getCoaches() ;

    List<IGeneralManager> getGeneralManagers();

}
