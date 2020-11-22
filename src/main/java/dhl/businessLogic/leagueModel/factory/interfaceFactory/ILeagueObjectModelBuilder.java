package dhl.businessLogic.leagueModel.factory.interfaceFactory;

import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;

public interface ILeagueObjectModelBuilder {

    ILeagueObjectModel getResult();

    void addLeagueName(String leagueName);

    void addConferences(List<IConference> conferences);

    void addFreeAgents(List<IPlayer> freeAgents);

    void addCoaches(List<ICoach> coaches);

    void addManagers(List<IGeneralManager> managers);

    void addGameConfig(IGameConfig gameConfig);
}
