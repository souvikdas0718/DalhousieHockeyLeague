package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PersistSameSeasonState;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersistSameSeasonStateTest {
    final static String LEAGUENAME="Dhl";
    SimulationContext simulationContext;
    PersistSameSeasonState persistSameSeasonState;
    GameContext gameState;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    ILeagueObjectModel leagueObjectModel;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
    }

    @Test
    public void PersistSameSeasonStateTest() {
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        Assertions.assertNotNull(persistSameSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        Assertions.assertNotNull(persistSameSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2022);
        persistSameSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getYear() == 2022);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setInMemoryLeague(leagueObjectModel);
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        persistSameSeasonState.seasonStateProcess();
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getInMemoryLeague().getLeagueName().equals(LEAGUENAME));
    }

    @Test
    public void seasonStateExitProcessTest() {
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        persistSameSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getCurrentSimulation() == persistSameSeasonState.getSimulationContext().getAdvanceTime());
    }
}
