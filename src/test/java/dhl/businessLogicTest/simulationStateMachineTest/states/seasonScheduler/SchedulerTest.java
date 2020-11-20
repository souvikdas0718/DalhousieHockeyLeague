package dhl.businessLogicTest.simulationStateMachineTest.states.seasonScheduler;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.interfaces.ISchedule;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.IStandings;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class SchedulerTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;

    @BeforeEach
    public void initObject() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = new Scheduler();
    }

    @Test
    public void getGameStandingsTest() {
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = new Scheduler();
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
    public void getFullSeasonScheduleTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = new Scheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);
        Assertions.assertFalse(scheduler.getFullSeasonSchedule().isEmpty());
    }

    @Test
    public void getPlayOffScheduleRound1Test() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);
        Assertions.assertFalse(scheduler.getPlayOffScheduleRound1().isEmpty());
    }

    @Test
    public void generateTeamListTest() {
        Scheduler scheduler = new Scheduler();
        ILeagueObjectModel leagueObjectModel = mockLeagueObjectModel.getLeagueObjectMock();

        scheduler.generateTeamList(leagueObjectModel);
        Assertions.assertTrue(scheduler.getTeamList().size() > 0);
        Assertions.assertTrue(scheduler.getDivisions().size() > 0);
        Assertions.assertTrue(scheduler.getConferences().size() > 0);
    }

    @Test
    public void generateTeamScheduleTest() {
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = new Scheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);

        for (ISchedule schedules : scheduler.getFullSeasonSchedule()) {
            Assertions.assertNotNull(schedules.getTeamOne());
            Assertions.assertNotNull(schedules.getTeamTwo());
        }
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().size() > 0);
    }

    @Test
    public void gameScheduleDatesTest() {

        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        IScheduler scheduler = new Scheduler();
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);

        LocalDate regularSeasonStartDate = LocalDate.of(2020, 10, 01);
        LocalDate localDate = LocalDate.of(2021, 02, 01);
        LocalDate regularSeasonEndDate = localDate.with(lastDayOfMonth())
                .with(previousOrSame(DayOfWeek.MONDAY));
        scheduler.gameScheduleDates(regularSeasonStartDate, regularSeasonEndDate);
        for (ISchedule schedules : scheduler.getFullSeasonSchedule()) {
            Assertions.assertNotNull(schedules.getGameDate());
        }
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().size() > 0);
    }

    @Test
    public void playOffsTest() {

        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        IScheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);
        int matchNumber = 1;
        for (ISchedule schedules : scheduler.getPlayOffScheduleRound1()) {
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

        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();

        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(8).getTeamOne().getTeamName().equals("Bruins"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(8).getTeamTwo().getTeamName().equals("Maple"));

        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(14).getTeamOne().getTeamName().equals("Maple"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(14).getTeamTwo().getTeamName().equals("Avalanche"));
    }

    @Test
    public void stanleyCupWinnerTest() {
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();

        LocalDate currentDate = LocalDate.now();

        Assertions.assertFalse(scheduler.stanleyCupWinner(currentDate));
    }
}