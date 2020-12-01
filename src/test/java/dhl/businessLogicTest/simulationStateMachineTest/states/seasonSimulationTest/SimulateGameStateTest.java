package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.GameConfigMock;
import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.SimulateGameState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class SimulateGameStateTest {
    SimulationContext simulationContext;
    SimulateGameState simulateGameState;
    GameContext gameState;
    List<ITeam> injuryCheckTeams;
    GameConfigMock gameConfigMock;
    IGameConfig ourGameConfig;
    LeagueObjectModelMocks modelMocks;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    MockAbstractFactory mockAbstractFactory;
    SchedulerAbstractFactory schedulerAbstractFactory;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    StandingsAbstractFactory standingsAbstractFactory;
    IStandingSystem standingSystem;

    @BeforeEach
    public void initObject() {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        mockAbstractFactory = MockAbstractFactory.instance();
        modelMocks = mockAbstractFactory.getLeagueObjectModelMock();
        gameConfigMock = new GameConfigMock();
        ourGameConfig = gameConfigMock.getGameConfigMock();
        injuryCheckTeams = modelMocks.getTeamArrayMock();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = schedulerAbstractFactory.getScheduler();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        simulateGameState = (SimulateGameState) seasonSimulationStateFactory.getSimulateGameState(simulationContext);
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
    }

    @Test
    public void SimulateGameStateTest() {
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2021);
        simulateGameState.setSimulationContext(simulationContext);
        Assertions.assertTrue(simulateGameState.getSimulationContext().getYear() == 2021);
    }

    @Test
    public void seasonStateProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays(195);
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);
        LocalDate regularSeasonStartDate = LocalDate.of(2020, 10, 01);
        LocalDate localDate = LocalDate.of(2021, 04, 01);
        LocalDate regularSeasonEndDate = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        scheduler.setSeasonStartDate(regularSeasonStartDate);
        scheduler.setSeasonEndDate(regularSeasonEndDate);
        scheduler.setFinalDay(LocalDate.of(2021, 06, 01));
        scheduler.gameScheduleDates(regularSeasonStartDate, regularSeasonEndDate);
        LocalDate playOffStartDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(
                TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standings, league);
        standingSystem.setStandingsList(scheduler.getGameStandings());
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setGameConfig(ourGameConfig);
        simulationContext.setMatchToSimulate(simulationContext.getRegularScheduler().getPlayOffScheduleRound1().get(0));
        simulateGameState = (SimulateGameState) seasonSimulationStateFactory.getSimulateGameState(simulationContext);
        simulateGameState.seasonStateProcess();
        Assertions.assertNotNull(simulateGameState.getSimulationContext().getFinalSchedule());
    }

    @Test
    public void seasonStateExitProcessTest() {
        simulateGameState.seasonStateExitProcess();
        Assertions.assertNotNull(simulateGameState.getSimulationContext().getTeamsPlayingInGame());
        Assertions.assertTrue(simulateGameState.getSimulationContext().getCurrentSimulation() == simulateGameState.getSimulationContext().getInjuryCheck());
    }
}
