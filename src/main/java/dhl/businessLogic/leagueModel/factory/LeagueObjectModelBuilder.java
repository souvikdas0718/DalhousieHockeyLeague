package dhl.businessLogic.leagueModel.factory;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;

public class LeagueObjectModelBuilder implements ILeagueObjectModelBuilder {
    private ILeagueObjectModel leagueObjectModel;

    public LeagueObjectModelBuilder() {
        leagueObjectModel = new LeagueObjectModel();
    }

    public ILeagueObjectModel getResult() {
        return leagueObjectModel;
    }

    public void addLeagueName(String leagueName) {
        this.leagueObjectModel.setLeagueName(leagueName);
    }

    public void addConferences(List<IConference> conferences) {
        this.leagueObjectModel.setConferences(conferences);
    }

    public void addFreeAgents(List<IPlayer> freeAgents) {
        this.leagueObjectModel.setFreeAgents(freeAgents);
    }

    public void addCoaches(List<ICoach> coaches) {
        this.leagueObjectModel.setCoaches(coaches);
    }

    public void addManagers(List<IGeneralManager> managers) {
        this.leagueObjectModel.setManagers(managers);
    }

    public void addGameConfig(IGameConfig gameConfig) {
        this.leagueObjectModel.setGameConfig(gameConfig);
    }

}
