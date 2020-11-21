package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AdvanceToNextSeasonState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AdvanceToNextSeasonStateTest {

    SimulationContext simulationContext;
    AdvanceToNextSeasonState advanceToNextSeasonState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        advanceToNextSeasonState = new AdvanceToNextSeasonState(simulationContext);
    }

    @Test
    public void AdvanceToNextSeasonStateTest() {
        advanceToNextSeasonState = new AdvanceToNextSeasonState(simulationContext);
        Assertions.assertNotNull(advanceToNextSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        advanceToNextSeasonState = new AdvanceToNextSeasonState(simulationContext);
        Assertions.assertNotNull(advanceToNextSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        advanceToNextSeasonState = new AdvanceToNextSeasonState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2014);
        advanceToNextSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getYear() == 2014);
    }


    @Test
    public void seasonStateEntryProcessTest() {
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2020);
        advanceToNextSeasonState = new AdvanceToNextSeasonState(simulationContext);
        advanceToNextSeasonState.seasonStateEntryProcess();
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getNumberOfDays() == 365);
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getEndOfSimulation().equals(LocalDate.of(simulationContext.getYear(), 9, 29)));
    }

//    @Test
//    public void seasonStateProcessTest() {
//        simulationContext.setNumberOfDays(365);
//        agingState = new AgingState(simulationContext);
//
//        advanceToNextSeasonState.seasonStateProcess();
//        AgingState.agingCalculation(simulationContext);
//    }

    @Test
    public void seasonStateExitProcessTest() {
        advanceToNextSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getCurrentSimulation() == advanceToNextSeasonState.getSimulationContext().getPersistsSeason());
    }
}
