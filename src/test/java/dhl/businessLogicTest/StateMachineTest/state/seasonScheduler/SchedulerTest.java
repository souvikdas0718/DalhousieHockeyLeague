package dhl.businessLogicTest.StateMachineTest.state.seasonScheduler;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISchedule;
import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.IStandings;
import dhl.businessLogic.simulationStateMachine.States.seasonScheduler.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class SchedulerTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;

    @BeforeEach
    public void initObject() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
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
        System.out.println(scheduler.getTeamList().size());
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

        List statistics = mockLeagueObjectModel.getPlayerArrayMock();
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();

        ITeam teamPlayOff1 = new Team("Bruins", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff2 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff3 = new Team("Hurricanes", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff4 = new Team("Flyers", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff5 = new Team("Blues", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff6 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff7 = new Team("Cancuks", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff8 = new Team("Flames", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        IScheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);

        scheduler.gameWinner(teamPlayOff1);
        scheduler.gameWinner(teamPlayoff2);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Bruins"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Maple"));

        scheduler.gameWinner(teamPlayoff3);
        scheduler.gameWinner(teamPlayoff4);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Hurricanes"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Flyers"));

        scheduler.gameWinner(teamPlayoff5);
        scheduler.gameWinner(teamPlayoff6);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Blues"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Avalanche"));

        scheduler.gameWinner(teamPlayoff7);
        scheduler.gameWinner(teamPlayoff8);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Cancuks"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Flames"));

        ITeam teamPlayOffRoundThree1 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree2 = new Team("Flyers", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree3 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree4 = new Team("Cancuks", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        scheduler.gameWinner(teamPlayOffRoundThree1);
        scheduler.gameWinner(teamPlayOffRoundThree2);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Maple"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Flyers"));

        scheduler.gameWinner(teamPlayOffRoundThree3);
        scheduler.gameWinner(teamPlayOffRoundThree4);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Avalanche"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Cancuks"));

        ITeam teamPlayOffRoundFour1 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundFour2 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        scheduler.gameWinner(teamPlayOffRoundFour1);
        scheduler.gameWinner(teamPlayOffRoundFour2);
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamOne().getTeamName().equals("Maple"));
        Assertions.assertTrue(scheduler.getPlayOffScheduleRound1().get(scheduler.getPlayOffScheduleRound1().size() - 1).getTeamTwo().getTeamName().equals("Avalanche"));
    }

    @Test
    public void stanleyCupWinnerTest() {

        List<IPlayer> statistics = mockLeagueObjectModel.getPlayerArrayMock();
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();

        ITeam teamPlayOff1 = new Team("Bruins", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff2 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff3 = new Team("Hurricanes", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff4 = new Team("Flyers", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff5 = new Team("Blues", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff6 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff7 = new Team("Cancuks", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayoff8 = new Team("Flames", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        IScheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);

        scheduler.gameWinner(teamPlayOff1);
        scheduler.gameWinner(teamPlayoff2);
        scheduler.gameWinner(teamPlayoff3);
        scheduler.gameWinner(teamPlayoff4);
        scheduler.gameWinner(teamPlayoff5);
        scheduler.gameWinner(teamPlayoff6);
        scheduler.gameWinner(teamPlayoff7);
        scheduler.gameWinner(teamPlayoff8);

        ITeam teamPlayOffRoundThree1 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree2 = new Team("Flyers", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree3 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundThree4 = new Team("Cancuks", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        scheduler.gameWinner(teamPlayOffRoundThree1);
        scheduler.gameWinner(teamPlayOffRoundThree2);
        scheduler.gameWinner(teamPlayOffRoundThree3);
        scheduler.gameWinner(teamPlayOffRoundThree4);

        ITeam teamPlayOffRoundFour1 = new Team("Maple", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);
        ITeam teamPlayOffRoundFour2 = new Team("Avalanche", league.getGeneralManagers().get(0).getGeneralManagerName(), league.getCoaches().get(0), statistics);

        scheduler.gameWinner(teamPlayOffRoundFour1);
        scheduler.gameWinner(teamPlayOffRoundFour2);

        LocalDate currentDate = LocalDate.now();

        Assertions.assertFalse(scheduler.stanleyCupWinner(currentDate));
    }
}