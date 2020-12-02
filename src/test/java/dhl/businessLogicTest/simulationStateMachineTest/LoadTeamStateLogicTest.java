package dhl.businessLogicTest.simulationStateMachineTest;

import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.MockDeserializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.states.LoadTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTeamStateLogicTest {

    StatesAbstractFactory statesAbstractFactory;
    ILoadTeamStateLogic objLoadTeamStateLogic;
    LeagueModelMockAbstractFactory factory;
    ILeagueObjectModel newInMemoryLeague;

    @BeforeEach
    public void initObject() {
        statesAbstractFactory = StatesAbstractFactory.instance();
        objLoadTeamStateLogic = statesAbstractFactory.createLoadTeamStateLogic();
        factory = LeagueModelMockAbstractFactory.instance();
        newInMemoryLeague = factory.createLeagueMock().getLeagueObjectModel();
    }

    @Test
    public void findTeamOfLeagueInDatabaseTest() throws Exception {
        GameContext ourGame = new GameContext();
        IDeserializeLeagueObjectModel deserializeLeagueObjectModel = new MockDeserializeLeagueObjectModel();
        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario");
        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);

        Assertions.assertEquals(true, iTeamFound);
    }

    @Test
    public void teamNotFoundOfLeagueInDatabaseTest() throws Exception {
        GameContext ourGame = new GameContext();
        IDeserializeLeagueObjectModel deserializeLeagueObjectModel = new MockDeserializeLeagueObjectModel();
        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario1");
        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);

        Assertions.assertEquals(false, iTeamFound);
    }

    @Test
    public void findTeamTest() {
        LeagueObjectModelMocks mocks = new LeagueObjectModelMocks();
        LoadTeamStateLogic loadTeamStateLogic = new LoadTeamStateLogic();
        ITeam objTeam = loadTeamStateLogic.findTeam(newInMemoryLeague, "Ontario");
        Assertions.assertEquals("Ontario", objTeam.getTeamName());
    }
}
