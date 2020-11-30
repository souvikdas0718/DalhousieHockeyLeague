package dhl.businessLogic.leagueModel.interfaceModel;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

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

    boolean checkIfLeagueModelValid(IValidation validation, ILeagueObjectModelValidation leagueObjectModelValidation) ;

    ILeagueObjectModel saveLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel, ILeagueObjectModelInput saveLeagueInput) ;

    ILeagueObjectModel loadLeagueObjectModel(IDeserializeLeagueObjectModel deserializeLeagueObjectModel, String leagueName, String teamName) ;

    ILeagueObjectModel updateLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel) ;
}
