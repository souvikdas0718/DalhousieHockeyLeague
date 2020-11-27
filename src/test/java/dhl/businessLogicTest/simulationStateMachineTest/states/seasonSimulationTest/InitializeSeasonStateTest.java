package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InitializeSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InitializeSeasonStateTest {
    LeagueObjectModel20TeamMocks model20TeamMocks;
    SimulationContext simulationContext;
    InitializeSeasonState initializeSeasonState;
    GameContext gameState;
    MockAbstractFactory mockAbstractFactory;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;

    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        initializeSeasonState = (InitializeSeasonState) seasonSimulationStateFactory.getInitializeSeasonState(simulationContext);
    }

    @Test
    public void InitializeSeasonStateTest() {
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        initializeSeasonState = (InitializeSeasonState) seasonSimulationStateFactory.getInitializeSeasonState(simulationContext);
        simulationContext.setYear(2021);
        initializeSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(initializeSeasonState.getSimulationContext().getYear() == 2021);
    }

    @Test
    public void seasonStateProcessTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        simulationContext.setYear(2021);
        simulationContext.setInMemoryLeague(league);
        initializeSeasonState = (InitializeSeasonState) seasonSimulationStateFactory.getInitializeSeasonState(simulationContext);
        initializeSeasonState.seasonStateProcess();
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext().getRegularScheduler().getFullSeasonSchedule().size());
        Assertions.assertEquals(initializeSeasonState.getSimulationContext().getRegularScheduler().getFullSeasonSchedule().get(0).getGameDate(), initializeSeasonState.getSimulationContext().getStartOfSimulation().plusDays(1));
    }

    @Test
    public void seasonStateExitProcessTest() {
        initializeSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(initializeSeasonState.getSimulationContext().getCurrentSimulation() == initializeSeasonState.getSimulationContext().getAdvanceTime());
    }

}
