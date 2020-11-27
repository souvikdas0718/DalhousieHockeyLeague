package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PersistSameSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersistSameSeasonStateTest {
    final static String LEAGUENAME = "Dhl";
    SimulationContext simulationContext;
    PersistSameSeasonState persistSameSeasonState;
    GameContext gameState;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    ILeagueObjectModel leagueObjectModel;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;

    @BeforeEach
    public void initObject() {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        persistSameSeasonState = (PersistSameSeasonState) seasonSimulationStateFactory.getPersistSameSeasonState(simulationContext);
    }

    @Test
    public void PersistSameSeasonStateTest() {
        Assertions.assertNotNull(persistSameSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(persistSameSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2022);
        persistSameSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getYear() == 2022);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setInMemoryLeague(leagueObjectModel);
        persistSameSeasonState = (PersistSameSeasonState) seasonSimulationStateFactory.getPersistSameSeasonState(simulationContext);
        persistSameSeasonState.seasonStateProcess();
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getInMemoryLeague().getLeagueName().equals(LEAGUENAME));
    }

    @Test
    public void seasonStateExitProcessTest() {
        persistSameSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getCurrentSimulation() == persistSameSeasonState.getSimulationContext().getAdvanceTime());
    }
}
