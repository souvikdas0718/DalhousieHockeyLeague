package dhl.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.database.interfaceDB.ILeagueObjectModelDB;

import java.util.List;

public interface ILeagueObjectModel {

    String getLeagueName();

    List<IConference> getConferences();

    List<IPlayer> getFreeAgents();

    List<ICoach> getCoaches() ;

    List<IGeneralManager> getGeneralManagers();

    IGameConfig getGameConfig();

    boolean checkIfLeagueModelValid(IValidation validation,ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception;

    ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, ILeagueObjectModelInput saveLeagueInput) throws Exception;

    ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, String leagueName, String teamName) throws Exception;

    ILeagueObjectModel updateLeagueObjectModel(ILeagueObjectModelDB leagueDatabase ) throws Exception;


}
