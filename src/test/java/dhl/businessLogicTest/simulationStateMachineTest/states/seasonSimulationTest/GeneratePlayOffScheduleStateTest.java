package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.GeneratePlayOffScheduleState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GeneratePlayOffScheduleStateTest {

    SimulationContext simulationContext;
    GeneratePlayOffScheduleState generatePlayOffScheduleState;
    GameContext gameState;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    MockAbstractFactory mockAbstractFactory;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    StandingsAbstractFactory standingsAbstractFactory;


    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        scheduler = schedulerAbstractFactory.getScheduler();
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        generatePlayOffScheduleState = (GeneratePlayOffScheduleState) seasonSimulationStateFactory.getGeneratePlayoffScheduleState(simulationContext);
        standingsAbstractFactory = StandingsAbstractFactory.instance();
    }

    @Test
    public void GeneratePlayOffScheduleStateTest() {
        Assertions.assertNotNull(generatePlayOffScheduleState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(generatePlayOffScheduleState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2020);
        generatePlayOffScheduleState.setSimulationContext(simulationContext);
        Assertions.assertTrue(generatePlayOffScheduleState.getSimulationContext().getYear() == 2020);
    }

    @Test
    public void seasonStateProcessTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        scheduler.setGameStandings(standings);
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setInMemoryLeague(league);
        simulationContext.setStandings(standings);
        IStandingSystem standingSystem = standingsAbstractFactory.getStandingSystem();
        standingSystem.setStandingsList(standings);
        generatePlayOffScheduleState.setSimulationContext(simulationContext);
        generatePlayOffScheduleState.seasonStateProcess();
        Assertions.assertTrue(generatePlayOffScheduleState.getSimulationContext().getRegularScheduler().getPlayOffScheduleRound1().size() > 0);
    }

    @Test
    public void seasonStateExitProcessTest() {
        generatePlayOffScheduleState.seasonStateExitProcess();
        Assertions.assertTrue(generatePlayOffScheduleState.getSimulationContext().getCurrentSimulation() == generatePlayOffScheduleState.getSimulationContext().getTraining());
    }
}
