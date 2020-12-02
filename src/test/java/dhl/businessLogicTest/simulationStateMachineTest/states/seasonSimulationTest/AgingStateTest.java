package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AgingState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
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
    ILeagueObjectModel leagueObjectModel;
    IGameConfig gameConfig;
    MockAbstractFactory mockAbstractFactory;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;

    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        gameConfig = leagueMock.getGameplayConfig();
        contextAbstractFactory = ContextAbstractFactory.instance();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        agingState = (AgingState) seasonSimulationStateFactory.getAgingState(simulationContext);
    }

    @Test
    public void AdvanceToNextSeasonStateTest() {
        Assertions.assertNotNull(agingState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(agingState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        agingState = (AgingState) seasonSimulationStateFactory.getAgingState(simulationContext);
        simulationContext.setYear(2015);
        agingState.setSimulationContext(simulationContext);
        Assertions.assertTrue(agingState.getSimulationContext().getYear() == 2015);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        simulationContext.setNumberOfDays(365);
        simulationContext.setStartOfSimulation(LocalDate.of(2020, 10, 01));
        agingState = (AgingState) seasonSimulationStateFactory.getAgingState(simulationContext);
        agingState.seasonStateProcess();
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerName().equals("Player0"));
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerStats().getAge() == 25);

        simulationContext.setNumberOfDays(365 * 2);
        agingState = (AgingState) seasonSimulationStateFactory.getAgingState(simulationContext);
        agingState.seasonStateProcess();
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerName().equals("Player0"));
        Assertions.assertTrue(agingState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerStats().getAge() == 26);
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        simulationContext.setStartOfSimulation(startOfSimulation);
        agingState = (AgingState) seasonSimulationStateFactory.getAgingState(simulationContext);
        agingState.seasonStateExitProcess();
        Assertions.assertTrue(agingState.getSimulationContext().getCurrentSimulation() == agingState.getSimulationContext().getPersistsSameSeason());
    }
}
