package dhl.businessLogicTest.leagueModelTests.factory;


import dhl.mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LeagueObjectModelBuilderTest {

    ILeagueObjectModelBuilder leagueBuilder;
    LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();

    @BeforeEach
    public void initialize() {
        leagueBuilder = new LeagueObjectModelBuilder();
    }

    @Test
    public void addLeagueName(){
        this.leagueBuilder.addLeagueName("Dalhousie Hockey League");
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        Assertions.assertEquals("Dalhousie Hockey League",leagueObjectModel.getLeagueName());
    }

    @Test
    public void addConferences(){
        List<IConference> mockConferences= leagueMock.getConferenceArrayMock();
        this.leagueBuilder.addConferences(mockConferences);
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        List<IConference> leagueConferences= leagueObjectModel.getConferences();

        Assertions.assertEquals(mockConferences.size(),leagueConferences.size());
    }

    @Test
    public void addFreeAgents(){
        List<IPlayer> mockFreeAgents= leagueMock.getFreeAgentArrayMock();
        this.leagueBuilder.addFreeAgents(mockFreeAgents);
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        List<IPlayer> leagueFreeAgents= leagueObjectModel.getFreeAgents();

        Assertions.assertEquals(mockFreeAgents.size(),leagueFreeAgents.size());
    }

    @Test
    public void addCoachesTest(){
        List<ICoach> mockCoaches= leagueMock.getCoaches();
        this.leagueBuilder.addCoaches(mockCoaches);
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        List<ICoach> leagueCoaches= leagueObjectModel.getCoaches();

        Assertions.assertEquals(mockCoaches.size(),leagueCoaches.size());
    }

    @Test
    public void addManagersTest(){
        List<IGeneralManager> mockManagers= leagueMock.getManagers();
        this.leagueBuilder.addManagers(mockManagers);
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        List<IGeneralManager> leagueManagers= leagueObjectModel.getGeneralManagers();

        Assertions.assertEquals(mockManagers.size(),leagueManagers.size());
    }

    @Test
    public void addGameConfig(){
        IGameConfig mockGameConfig= leagueMock.getGameConfig();
        this.leagueBuilder.addGameConfig(mockGameConfig);
        ILeagueObjectModel leagueObjectModel = leagueBuilder.getResult();
        IGameConfig gameConfig= leagueObjectModel.getGameConfig();

        Assertions.assertEquals("trading",gameConfig.getTrading());
    }
}
