package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PersistSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class PersistSeasonStateTest {
    final static String LEAGUENAME = "Dhl";
    SimulationContext simulationContext;
    PersistSeasonState persistSeasonState;
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
        persistSeasonState = (PersistSeasonState) seasonSimulationStateFactory.getPersistSeasonState(simulationContext);
    }

    @Test
    public void PersistSeasonStateTest() {
        Assertions.assertNotNull(persistSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(persistSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2021);
        persistSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(persistSeasonState.getSimulationContext().getYear() == 2021);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setInMemoryLeague(leagueObjectModel);
        persistSeasonState = new PersistSeasonState(simulationContext);
        persistSeasonState.seasonStateProcess();
        Assertions.assertTrue(persistSeasonState.getSimulationContext().getInMemoryLeague().getLeagueName().equals(LEAGUENAME));
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
        LocalDate currentDate = LocalDate.now();
        long numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays((int) numberOfDays);
        simulationContext.setYear(2020);
        persistSeasonState = (PersistSeasonState) seasonSimulationStateFactory.getPersistSeasonState(simulationContext);
        persistSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(persistSeasonState.getSimulationContext().getCurrentSimulation() == persistSeasonState.getSimulationContext().getInitializeSeason());

        currentDate = startOfSimulation.plusYears(1);
        currentDate = currentDate.minusDays(1);
        numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setNumberOfDays((int) numberOfDays);
        persistSeasonState = (PersistSeasonState) seasonSimulationStateFactory.getPersistSeasonState(simulationContext);
        persistSeasonState.seasonStateExitProcess();
        Assertions.assertFalse(persistSeasonState.getSimulationContext().isSeasonInProgress());
    }
}
