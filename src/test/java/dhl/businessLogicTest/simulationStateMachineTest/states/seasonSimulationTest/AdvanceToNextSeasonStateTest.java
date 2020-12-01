package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.AdvanceToNextSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AdvanceToNextSeasonStateTest {

    SimulationContext simulationContext;
    AdvanceToNextSeasonState advanceToNextSeasonState;
    GameContext gameState;
    ILeagueObjectModel leagueObjectModel;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    IGameConfig gameConfig;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;

    @BeforeEach
    public void initObject() {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        advanceToNextSeasonState = (AdvanceToNextSeasonState) seasonSimulationStateFactory.getAdvanceToNextSeasonState(simulationContext);
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        gameConfig = leagueMock.getGameplayConfig();
    }

    @Test
    public void AdvanceToNextSeasonStateTest() {
        Assertions.assertNotNull(advanceToNextSeasonState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(advanceToNextSeasonState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2014);
        advanceToNextSeasonState = (AdvanceToNextSeasonState) seasonSimulationStateFactory.getAdvanceToNextSeasonState(simulationContext);
        advanceToNextSeasonState.setSimulationContext(simulationContext);
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getYear() == 2014);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setYear(2020);
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        simulationContext.setNumberOfDays(365);
        advanceToNextSeasonState = (AdvanceToNextSeasonState) seasonSimulationStateFactory.getAdvanceToNextSeasonState(simulationContext);
        advanceToNextSeasonState.seasonStateProcess();
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getNumberOfDays() == 365);
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getEndOfSimulation().equals(LocalDate.of(simulationContext.getYear(), 9, 29)));
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerName().equals("Player0"));
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getInMemoryLeague().getFreeAgents().get(0).getPlayerStats().getAge() == 25);
    }

    @Test
    public void seasonStateExitProcessTest() {
        advanceToNextSeasonState.seasonStateExitProcess();
        Assertions.assertTrue(advanceToNextSeasonState.getSimulationContext().getCurrentSimulation() == advanceToNextSeasonState.getSimulationContext().getPersistsSeason());
    }
}
