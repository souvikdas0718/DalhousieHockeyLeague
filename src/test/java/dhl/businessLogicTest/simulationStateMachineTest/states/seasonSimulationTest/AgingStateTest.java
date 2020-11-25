package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AgingState;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AgingStateTest {

    LeagueObjectModel20TeamMocks model20TeamMocks;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    IScheduler scheduler;
    SimulationContext simulationContext;
    AgingState agingState;
    GameContext gameState;
    ILeagueObjectModel leagueObjectModel;
    IGameConfig gameConfig;

    @BeforeEach
    public void initObject() {
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        gameConfig = leagueMock.getGameplayConfig();
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
    public void seasonStateProcessTest() {
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        simulationContext.setNumberOfDays(365);
        agingState = new AgingState(simulationContext);
        agingState.seasonStateProcess();
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerName().equals("Player0"));
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerStats().getAge() == 25);

        simulationContext.setNumberOfDays(365*2);
        agingState = new AgingState(simulationContext);
        agingState.seasonStateProcess();
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerName().equals("Player0"));
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerStats().getAge() == 26);
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
