package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AdvanceTimeState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AdvanceTimeStateTest {

    SimulationContext simulationContext;
    AdvanceTimeState advanceTimeState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);

    }

    @Test
    public void AdvanceTimeStateTest() {
        advanceTimeState = new AdvanceTimeState(simulationContext);
        Assertions.assertNotNull(advanceTimeState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        advanceTimeState = new AdvanceTimeState(simulationContext);
        Assertions.assertNotNull(advanceTimeState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        advanceTimeState = new AdvanceTimeState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2013);
        advanceTimeState.setSimulationContext(simulationContext);
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getYear() == 2013);
    }


    @Test
    public void seasonStateEntryProcess() {

    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext = new SimulationContext(gameState);
        simulationContext.setNumberOfDays(20);
        advanceTimeState = new AdvanceTimeState(simulationContext);
        advanceTimeState.seasonStateProcess();
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getNumberOfDays() == 21);
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays(184);
        advanceTimeState = new AdvanceTimeState(simulationContext);
        advanceTimeState.seasonStateExitProcess();
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getCurrentSimulation() == advanceTimeState.getSimulationContext().getPlayoffSchedule());
    }
}
