package dhl.businessLogic.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.database.interfaceDB.ILeagueObjectModelDB;

import java.util.List;

public interface ILeagueObjectModel {

    String getLeagueName();

    List<IConference> getConferences();

    List<IPlayer> getFreeAgents();

    List<ICoach> getCoaches();

    List<IGeneralManager> getGeneralManagers();

    IGameConfig getGameConfig();

    void setLeagueName(String leagueName);

    void setConferences(List<IConference> conferences);

    void setFreeAgents(List<IPlayer> freeAgents);

    void setManagers(List managers);

    void setCoaches(List<ICoach> coaches);

    void setGameConfig(IGameConfig gameConfig);

    boolean checkIfLeagueModelValid(IValidation validation, ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception;

    ILeagueObjectModel saveLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel, ILeagueObjectModelInput saveLeagueInput) throws Exception;

    ILeagueObjectModel loadLeagueObjectModel(IDeserializeLeagueObjectModel deserializeLeagueObjectModel, String leagueName, String teamName) throws Exception;

    ILeagueObjectModel updateLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel) throws Exception;


}
