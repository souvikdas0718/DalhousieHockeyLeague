package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;

import java.util.ArrayList;
import java.util.List;

public class LeagueMock {

    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;
    ConferenceMock conferenceMock;
    FreeAgentMock freeAgentMock;
    CoachMock coachMock;
    ManagerMock managerMock;
    GameplayConfigMock gameplayConfigMock;
    ILeagueObjectModelBuilder leagueBuilder;
    ILeagueObjectModelDirector leagueDirector;


    public LeagueMock(){
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        leagueBuilder = new LeagueObjectModelBuilder();
        leagueDirector = new LeagueObjectModelDirector(leagueBuilder);

        conferenceMock = mockFactory.createConferenceMock();
        freeAgentMock = mockFactory.createFreeAgentMock();
        coachMock = mockFactory.createCoachMock();
        managerMock = mockFactory.createManagerMock();
        gameplayConfigMock = mockFactory.createGameplayConfig();

    }

    public ILeagueObjectModel getLeagueObjectModel()  {

        List<IConference> conferences = getConferences();
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }

    public List<IConference> getConferences(){
        return conferenceMock.getConferences();
    }

    public  List<IPlayer> getFreeAgents(){
        return freeAgentMock.getFreeAgents();
    }

    public  List<ICoach> getCoaches(){
        return coachMock.getCoaches();
    }

    public  List<IGeneralManager> getManagers(){
        return managerMock.getManagers();
    }

    public IGameConfig getGameplayConfig(){
        return gameplayConfigMock.getGameplayConfig();
    }

    public ILeagueObjectModel getLeagueObjectModelDuplicateConference()  {
        List<IConference> conferences = getConferences();
        for(int i=0;i<2;i++){
            conferences.add( conferenceMock.getConference());
        }
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }

    public ILeagueObjectModel getLeagueObjectModelOddConf()  {
        List<IConference> conferences = getConferences();
        conferences.add( conferenceMock.getConference());
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }



}
