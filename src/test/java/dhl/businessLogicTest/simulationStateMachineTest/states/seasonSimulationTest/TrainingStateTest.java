package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.GameConfigMock;
import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.TrainingState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TrainingStateTest {

    SimulationContext simulationContext;
    TrainingState trainingState;
    GameContext gameState;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    IGameConfig gameConfig;
    GameConfigMock gameConfigMock;
    AgingAbstractFactory agingFactory;
    Injury injury;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    ILeagueObjectModel leagueObjectModel;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    MockAbstractFactory mockAbstractFactory;

    @BeforeEach
    public void initObject() throws Exception {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        trainingState = (TrainingState) seasonSimulationStateFactory.getTrainingState(simulationContext);
        mockAbstractFactory = MockAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        agingFactory = AgingAbstractFactory.instance();
        injury = (Injury) agingFactory.createInjury();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        gameConfig = gameplayConfigMock.getAgingGameConfig();
    }

    @Test
    public void TrainingStateTest() {
        Assertions.assertNotNull(trainingState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(trainingState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2022);
        trainingState.setSimulationContext(simulationContext);
        Assertions.assertTrue(trainingState.getSimulationContext().getYear() == 2022);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setDaysSinceLastTraining(192);
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInjurySystem(injury);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        trainingState = (TrainingState) seasonSimulationStateFactory.getTrainingState(simulationContext);
        trainingState.seasonStateProcess();
        Assertions.assertEquals(-1, trainingState.getSimulationContext().getInMemoryLeague().getConferences().get(0).getDivisions().get(0).getTeams().get(0).getPlayers().get(0).getPlayerInjuredDays());
    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
        LocalDate seasonStartDate = LocalDate.of(2020, 10, 1);
        LocalDate regularSeasonEndDate = LocalDate.of(2021, 04, 3);

        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInjurySystem(injury);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        simulationContext.setSeasonEndDate(regularSeasonEndDate);
        simulationContext.setSeasonStartDate(startOfSimulation);
        scheduler.setSeasonEndDate(regularSeasonEndDate);
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays(196);
        simulationContext.setYear(2020);
        trainingState = (TrainingState) seasonSimulationStateFactory.getTrainingState(simulationContext);
        trainingState.seasonStateExitProcess();
        Assertions.assertNotNull(trainingState.getSimulationContext().getFinalSchedule());
    }
}
