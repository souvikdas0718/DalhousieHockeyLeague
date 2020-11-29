package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.factory.MockAbstractFactory;
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
        simulationContext.setNumberOfDays(10);
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
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setGameConfig(ourGameConfig);
        simulateGameState = (SimulateGameState) seasonSimulationStateFactory.getSimulateGameState(simulationContext);
        simulateGameState.seasonStateProcess();
        Assertions.assertTrue(scheduler.getGameStandings().get(0).getTeam().getTeamName().equals("Bruins"));
        Assertions.assertFalse(scheduler.getGameStandings().get(0).getLoss() == 4);
        Assertions.assertTrue(scheduler.getGameStandings().get(16).getTeam().getTeamName().equals("Oilers"));
        Assertions.assertFalse(scheduler.getGameStandings().get(16).getWins() == 44);
        Assertions.assertFalse(scheduler.getGameStandings().get(16).getPoints() == 88);

        simulationContext.setNumberOfDays(195);
        simulateGameState = (SimulateGameState) seasonSimulationStateFactory.getSimulateGameState(simulationContext);
        simulateGameState.seasonStateProcess();

        simulationContext.setNumberOfDays(196);
        simulateGameState = (SimulateGameState) seasonSimulationStateFactory.getSimulateGameState(simulationContext);
        simulateGameState.seasonStateProcess();
        int lengthOfPlayOffList = scheduler.getPlayOffScheduleRound1().size();

        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(lengthOfPlayOffList - 1).getTeamOne().getTeamName().equals("BlueJackets"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(lengthOfPlayOffList - 1).getTeamTwo().getTeamName().equals("Maple"));
    }

    @Test
    public void seasonStateExitProcessTest() {
        simulateGameState.seasonStateExitProcess();
        Assertions.assertNotNull(simulateGameState.getSimulationContext().getTeamsPlayingInGame());
        Assertions.assertTrue(simulateGameState.getSimulationContext().getCurrentSimulation() == simulateGameState.getSimulationContext().getInjuryCheck());
    }
}
