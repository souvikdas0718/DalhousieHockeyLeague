package dhl.businessLogicTest.simulationStateMachineTest.states.seasonScheduler;

import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class SchedulerTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    IScheduler scheduler2;
    List<IPlayer> statistics;
    MockAbstractFactory mockAbstractFactory;
    SchedulerAbstractFactory schedulerAbstractFactory;
    LeagueModelAbstractFactory leagueModelAbstractFactory;

    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = schedulerAbstractFactory.getScheduler();
        scheduler2 = schedulerAbstractFactory.getScheduler();
        statistics = mockLeagueObjectModel.getPlayerArrayMock();
        leagueModelAbstractFactory = LeagueModelAbstractFactory.instance();
        scheduler2 = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
    }

    @Test
    public void getGameStandingsTest() {
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        scheduler.setGameStandings(standings);
        Assertions.assertEquals(scheduler.getGameStandings().size(), 20);
    }

    @Test
    public void setGameStandingsTest() {
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();

        scheduler.setGameStandings(standings);
        Assertions.assertEquals(scheduler.getGameStandings().size(), 20);
    }

    @Test
    public void getCurrentDate() {
        LocalDate date = LocalDate.now();
        scheduler.setCurrentDate(date);
        Assertions.assertTrue(scheduler.getCurrentDate().equals(date));
    }

    @Test
    public void setCurrentDate() {
        LocalDate date = LocalDate.now();
        scheduler.setCurrentDate(date);
        Assertions.assertTrue(scheduler.getCurrentDate().equals(date));
    }

    @Test
    public void getSeasonStartDateTest() {
        LocalDate startDate = LocalDate.of(2020, 10, 01);
        scheduler.setSeasonStartDate(startDate);
        Assertions.assertTrue(scheduler.getSeasonStartDate().equals(startDate));
    }

    @Test
    public void setSeasonStartDateTest() {
        LocalDate startDate = LocalDate.of(2020, 10, 01);
        scheduler.setSeasonStartDate(startDate);
        Assertions.assertTrue(scheduler.getSeasonStartDate().equals(startDate));
    }

    @Test
    public void getSeasonEndDateTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate endOfRegularSeasonDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        scheduler.setSeasonEndDate(endOfRegularSeasonDate);
        Assertions.assertTrue(scheduler.getSeasonEndDate().equals(endOfRegularSeasonDate));
    }

    @Test
    public void setSeasonEndDateTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate endOfRegularSeasonDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        scheduler.setSeasonEndDate(endOfRegularSeasonDate);
        Assertions.assertTrue(scheduler.getSeasonEndDate().equals(endOfRegularSeasonDate));
    }

    @Test
    public void setFullSeasonScheduleTest() {
        scheduler2.setFullSeasonSchedule(scheduler2.getPlayOffScheduleRound1());
        Assertions.assertNotNull(scheduler2.getFullSeasonSchedule());
    }

    @Test
    public void getPlayOffStartDateTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStartDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        playOffStartDate = playOffStartDate.plusDays(7);
        scheduler.setPlayOffStartDate(playOffStartDate);
        Assertions.assertTrue(scheduler.getPlayOffStartDate().equals(playOffStartDate));
    }

    @Test
    public void setPlayOffStartDateTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStartDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        playOffStartDate = playOffStartDate.plusDays(7);
        scheduler.setPlayOffStartDate(playOffStartDate);
        Assertions.assertTrue(scheduler.getPlayOffStartDate().equals(playOffStartDate));
    }

    @Test
    public void getFinalDayTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate finalsDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        finalsDate = finalsDate.plusDays(14);
        scheduler.setFinalDay(finalsDate);
        Assertions.assertTrue(scheduler.getFinalDay().equals(finalsDate));
    }

    @Test
    public void setFinalDayTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate finalsDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        finalsDate = finalsDate.plusDays(14);
        scheduler.setFinalDay(finalsDate);
        Assertions.assertTrue(scheduler.getFinalDay().equals(finalsDate));
    }

    @Test
    public void getFinalMatchDayTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate finalsDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        finalsDate = finalsDate.plusDays(14);
        scheduler.setFinalMatchDate(finalsDate);
        Assertions.assertTrue(scheduler.getFinalMatchDate().equals(finalsDate));
    }

    @Test
    public void setFinalMatchDayTest() {
        LocalDate endDate = LocalDate.of(2021, 04, 01);
        LocalDate finalsDate = endDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
        finalsDate = finalsDate.plusDays(14);
        scheduler.setFinalMatchDate(finalsDate);
        Assertions.assertTrue(scheduler.getFinalMatchDate().equals(finalsDate));
    }

    @Test
    public void getFullSeasonScheduleTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);
        Assertions.assertFalse(scheduler.getFullSeasonSchedule().isEmpty());
    }

    @Test
    public void getPlayOffScheduleRound1Test() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();

        LocalDate playOffStartDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)).with(
                TemporalAdjusters.next(DayOfWeek.SATURDAY));

        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standings, league);

        Assertions.assertFalse(scheduler.getPlayOffScheduleRound1().isEmpty());
    }

    @Test
    public void generateTeamListTest() {
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        ILeagueObjectModel leagueObjectModel = mockLeagueObjectModel.getLeagueObjectMock();

        scheduler.generateTeamList(leagueObjectModel);
        Assertions.assertTrue(scheduler.getTeamList().size() > 0);
        Assertions.assertTrue(scheduler.getDivisions().size() > 0);
        Assertions.assertTrue(scheduler.getConferences().size() > 0);
    }

    @Test
    public void generateTeamScheduleTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);

        for (ISeasonSchedule schedules : scheduler.getFullSeasonSchedule()) {
            Assertions.assertNotNull(schedules.getTeamOne());
            Assertions.assertNotNull(schedules.getTeamTwo());
        }
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().size() > 0);
    }

    @Test
    public void gameScheduleDatesTest() {

        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);

        LocalDate regularSeasonStartDate = LocalDate.of(2020, 10, 01);
        LocalDate localDate = LocalDate.of(2021, 04, 01);
        LocalDate regularSeasonEndDate = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(regularSeasonStartDate, regularSeasonEndDate);
        for (ISeasonSchedule schedules : scheduler.getFullSeasonSchedule()) {
            Assertions.assertNotNull(schedules.getGameDate());
        }
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().size() > 0);
    }

    @Test
    public void playOffsTest() {

        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();

        LocalDate playOffStartDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(
                TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standings, league);
        int matchNumber = 1;
        for (ISeasonSchedule schedules : scheduler.getPlayOffScheduleRound1()) {
            if (matchNumber == 1) {
                Assertions.assertTrue(schedules.getTeamOne().getTeamName().equals("Bruins"));
                Assertions.assertTrue(schedules.getTeamTwo().getTeamName().equals("BlueJackets"));
            } else if (matchNumber == 5) {
                Assertions.assertTrue(schedules.getTeamOne().getTeamName().equals("Blues"));
                Assertions.assertTrue(schedules.getTeamTwo().getTeamName().equals("Jets"));
            }
            matchNumber = matchNumber + 1;
        }
    }

    @Test
    public void gameWinnerTest() {

        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = schedulerAbstractFactory.getScheduler();

        LocalDate playOffStartDate = LocalDate.of(2021, 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(
                TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standings, league);

        IGeneralManager manager = league.getGeneralManagers().get(0);

        ITeam teamPlayOff1 = leagueModelAbstractFactory.createTeam("Bruins", manager, league.getCoaches().get(0), statistics);
        ITeam teamPlayoff2 = leagueModelAbstractFactory.createTeam("Maple", manager, league.getCoaches().get(0), statistics);
        ITeam teamPlayoff3 = leagueModelAbstractFactory.createTeam("Hurricanes", manager, league.getCoaches().get(0), statistics);
        ITeam teamPlayoff4 = leagueModelAbstractFactory.createTeam("Flyers", manager, league.getCoaches().get(0), statistics);

        scheduler.gameWinner(teamPlayOff1);
        scheduler.gameWinner(teamPlayoff2);
        scheduler.gameWinner(teamPlayoff3);
        scheduler.gameWinner(teamPlayoff4);

        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(8).getTeamOne().getTeamName().equals("Bruins"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(8).getTeamTwo().getTeamName().equals("Maple"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(8).getGameDate().equals(LocalDate.of(2021, 04, 22)));

        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(9).getTeamOne().getTeamName().equals("Hurricanes"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(9).getTeamTwo().getTeamName().equals("Flyers"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(9).getGameDate().equals(LocalDate.of(2021, 04, 23)));
    }

    @Test
    public void stanleyCupWinnerTest() {
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        LocalDate currentDate = LocalDate.now();

        Assertions.assertFalse(scheduler.stanleyCupWinner(currentDate));
    }
}