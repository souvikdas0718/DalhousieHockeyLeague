package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PersistSameSeasonState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersistSameSeasonStateTest {
    SimulationContext simulationContext;
    PersistSameSeasonState persistSameSeasonState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
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
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {

    }

    @Test
    public void seasonStateExitProcessTest() {
        persistSameSeasonState = new PersistSameSeasonState(simulationContext);
        persistSameSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(persistSameSeasonState.getSimulationContext().getCurrentSimulation() == persistSameSeasonState.getSimulationContext().getAdvanceTime());
    }
}
