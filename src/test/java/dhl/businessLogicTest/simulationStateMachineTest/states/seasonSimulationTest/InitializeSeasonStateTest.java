package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InitializeSeasonState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InitializeSeasonStateTest {
    LeagueObjectModel20TeamMocks model20TeamMocks;
    SimulationContext simulationContext;
    InitializeSeasonState initializeSeasonState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
    }

    @Test
    public void InitializeSeasonStateTest() {
        initializeSeasonState = new InitializeSeasonState(simulationContext);
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        initializeSeasonState = new InitializeSeasonState(simulationContext);
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        initializeSeasonState = new InitializeSeasonState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2021);
        initializeSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(initializeSeasonState.getSimulationContext().getYear() == 2021);
    }

    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        simulationContext.setYear(2021);
        simulationContext.setInMemoryLeague(league);
        initializeSeasonState = new InitializeSeasonState(simulationContext);
        initializeSeasonState.seasonStateProcess();
        Assertions.assertNotNull(initializeSeasonState.getSimulationContext().getRegularScheduler().getFullSeasonSchedule().size());
        Assertions.assertEquals(initializeSeasonState.getSimulationContext().getRegularScheduler().getFullSeasonSchedule().get(0).getGameDate(), initializeSeasonState.getSimulationContext().getStartOfSimulation().plusDays(1));
    }

    @Test
    public void seasonStateExitProcessTest() {
        initializeSeasonState = new InitializeSeasonState(simulationContext);
        initializeSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(initializeSeasonState.getSimulationContext().getCurrentSimulation() == initializeSeasonState.getSimulationContext().getAdvanceTime());
    }

}
