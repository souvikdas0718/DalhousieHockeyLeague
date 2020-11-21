package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AgingState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AgingStateTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    SimulationContext simulationContext;
    AgingState agingState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
    }

    @Test
    public void AdvanceToNextSeasonStateTest() {
        agingState = new AgingState(simulationContext);
        Assertions.assertNotNull(agingState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        agingState = new AgingState(simulationContext);
        Assertions.assertNotNull(agingState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        agingState = new AgingState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2015);
        agingState.setSimulationContext(simulationContext);
        Assertions.assertTrue(agingState.getSimulationContext().getYear() == 2015);
    }

    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        //agingCalculation(simulationContext);
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);

        simulationContext.setPlayOffScheduleRound1(scheduler);
        simulationContext.setStartOfSimulation(startOfSimulation);
        agingState = new AgingState(simulationContext);
        agingState.seasonStateExitProcess();

        Assertions.assertTrue(agingState.getSimulationContext().getCurrentSimulation() == agingState.getSimulationContext().getPersistsSeason());

    }
}
