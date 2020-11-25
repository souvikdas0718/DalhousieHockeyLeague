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
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.SimulateGameState;
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
    MockAbstractFactory mockAbstractFactory;
    SchedulerAbstractFactory schedulerAbstractFactory;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        modelMocks = new LeagueObjectModelMocks();
        gameConfigMock = new GameConfigMock();
        ourGameConfig = gameConfigMock.getGameConfigMock();
        injuryCheckTeams = modelMocks.getTeamArrayMock();
        mockAbstractFactory = MockAbstractFactory.instance();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = schedulerAbstractFactory.getScheduler();
    }

    @Test
    public void SimulateGameStateTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2021);
        simulateGameState.setSimulationContext(simulationContext);
        Assertions.assertTrue(simulateGameState.getSimulationContext().getYear() == 2021);
    }


    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext = new SimulationContext(gameState);
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
//        System.out.println(scheduler.getFullSeasonSchedule());
        LocalDate playOffStartDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(
                TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standings, league);
        simulationContext.setRegularScheduler(scheduler);
        simulationContext.setGameConfig(ourGameConfig);
        simulateGameState = new SimulateGameState(simulationContext);
        simulateGameState.seasonStateProcess();
        Assertions.assertTrue(scheduler.getGameStandings().get(0).getTeam().getTeamName().equals("Bruins"));
        Assertions.assertTrue(scheduler.getGameStandings().get(0).getLoss() == 4);
        Assertions.assertTrue(scheduler.getGameStandings().get(16).getTeam().getTeamName().equals("Oilers"));
        Assertions.assertTrue(scheduler.getGameStandings().get(16).getWins() == 44);
        Assertions.assertTrue(scheduler.getGameStandings().get(16).getPoints() == 88);

//        simulationContext.setNumberOfDays(195);
//        simulateGameState = new SimulateGameState(simulationContext);
//        simulateGameState.seasonStateProcess();

//        simulationContext.setNumberOfDays(196);
//        simulateGameState = new SimulateGameState(simulationContext);
//        simulateGameState.seasonStateProcess();
//        System.out.println(simulateGameState.getSimulationContext().getPlayOffScheduleRound1().getPlayOffScheduleRound1().size());

//        simulationContext.set

//        simulationContext.setRegularScheduler(scheduler.getFullSeasonSchedule());
//        simulateGameState = new SimulateGameState(simulationContext);

//        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
//        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
//        winDecider(currentDate, scheduler);
    }

    @Test
    public void seasonStateExitProcessTest() {
//        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
//        LocalDate currentDate = LocalDate.now();
//        long numberOfDays = DAYS.between(startOfSimulation, currentDate);
//        simulationContext.setStartOfSimulation(startOfSimulation);
//        simulationContext.setNumberOfDays((int)numberOfDays);
//        simulationContext.setYear(2020);
//        simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
        simulateGameState = new SimulateGameState(simulationContext);
        simulateGameState.seasonStateExitProcess();
        Assertions.assertNotNull(simulateGameState.getSimulationContext().getTeamsPlayingInGame());
        Assertions.assertTrue(simulateGameState.getSimulationContext().getCurrentSimulation() == simulateGameState.getSimulationContext().getInjuryCheck());

//        this.simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
//        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());

    }
}
