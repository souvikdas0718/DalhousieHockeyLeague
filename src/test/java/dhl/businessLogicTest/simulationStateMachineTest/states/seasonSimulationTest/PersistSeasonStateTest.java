package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PersistSeasonState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class PersistSeasonStateTest {
    SimulationContext simulationContext;
    PersistSeasonState persistSeasonState;
    GameContext gameState;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
    }

    @Test
    public void PersistSeasonStateTest() {
        persistSeasonState = new PersistSeasonState(simulationContext);
        Assertions.assertNotNull(persistSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        persistSeasonState = new PersistSeasonState(simulationContext);
        Assertions.assertNotNull(persistSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        persistSeasonState = new PersistSeasonState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2021);
        persistSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(persistSeasonState.getSimulationContext().getYear() == 2021);
    }


    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        //Store in DB
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
        LocalDate currentDate = LocalDate.now();
        long numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays((int)numberOfDays);
        simulationContext.setYear(2020);
        persistSeasonState = new PersistSeasonState(simulationContext);
        persistSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(persistSeasonState.getSimulationContext().getCurrentSimulation() == persistSeasonState.getSimulationContext().getAdvanceTime());

        currentDate = startOfSimulation.plusYears(1);
        currentDate = currentDate.minusDays(1);
        numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setNumberOfDays((int)numberOfDays);
        persistSeasonState = new PersistSeasonState(simulationContext);
        persistSeasonState.seasonStateExitProcess();
        Assertions.assertFalse(persistSeasonState.getSimulationContext().isSeasonInProgress());
    }
}
