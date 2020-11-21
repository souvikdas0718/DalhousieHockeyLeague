package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.IStandings;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.GeneratePlayOffScheduleState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GeneratePlayOffScheduleStateTest {

    SimulationContext simulationContext;
    GeneratePlayOffScheduleState generatePlayOffScheduleState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;


    @BeforeEach
    public void initObject() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = new Scheduler();
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        generatePlayOffScheduleState = new GeneratePlayOffScheduleState(simulationContext);
    }

    @Test
    public void GeneratePlayOffScheduleStateTest() {
        generatePlayOffScheduleState = new GeneratePlayOffScheduleState(simulationContext);
        Assertions.assertNotNull(generatePlayOffScheduleState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        generatePlayOffScheduleState = new GeneratePlayOffScheduleState(simulationContext);
        Assertions.assertNotNull(generatePlayOffScheduleState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        generatePlayOffScheduleState = new GeneratePlayOffScheduleState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2020);
        generatePlayOffScheduleState.setSimulationContext(simulationContext);
        Assertions.assertTrue(generatePlayOffScheduleState.getSimulationContext().getYear() == 2020);
    }

    @Test
    public void seasonStateProcess() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = new Scheduler();
        scheduler.setGameStandings(standings);
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setInMemoryLeague(league);
        simulationContext.setStandings(standings);
        generatePlayOffScheduleState.setSimulationContext(simulationContext);
        generatePlayOffScheduleState.seasonStateProcess();
        Assertions.assertNotNull(scheduler.getPlayOffScheduleRound1());
    }

    @Test
    public void seasonStateExitProcessTest() {
        generatePlayOffScheduleState.seasonStateExitProcess();
        Assertions.assertTrue(generatePlayOffScheduleState.getSimulationContext().getCurrentSimulation() == generatePlayOffScheduleState.getSimulationContext().getTraining());
    }
}
