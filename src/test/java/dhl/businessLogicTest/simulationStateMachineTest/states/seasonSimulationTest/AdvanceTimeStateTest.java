package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AdvanceTimeState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AdvanceTimeStateTest {

    SimulationContext simulationContext;
    AdvanceTimeState advanceTimeState;
    GameContext gameState;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;

    @BeforeEach
    public void initObject() {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
    }

    @Test
    public void AdvanceTimeStateTest() {
        advanceTimeState = (AdvanceTimeState) seasonSimulationStateFactory.getAdvanceTimeState(simulationContext);
        Assertions.assertNotNull(advanceTimeState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        advanceTimeState = (AdvanceTimeState) seasonSimulationStateFactory.getAdvanceTimeState(simulationContext);
        Assertions.assertNotNull(advanceTimeState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2013);
        advanceTimeState = (AdvanceTimeState) seasonSimulationStateFactory.getAdvanceTimeState(simulationContext);
        advanceTimeState.setSimulationContext(simulationContext);
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getYear() == 2013);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setNumberOfDays(20);
        advanceTimeState = (AdvanceTimeState) seasonSimulationStateFactory.getAdvanceTimeState(simulationContext);
        advanceTimeState.seasonStateProcess();
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getNumberOfDays() == 21);
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays(184);
        advanceTimeState = (AdvanceTimeState) seasonSimulationStateFactory.getAdvanceTimeState(simulationContext);
        advanceTimeState.seasonStateExitProcess();
        Assertions.assertTrue(advanceTimeState.getSimulationContext().getCurrentSimulation() == advanceTimeState.getSimulationContext().getTraining());
    }
}
