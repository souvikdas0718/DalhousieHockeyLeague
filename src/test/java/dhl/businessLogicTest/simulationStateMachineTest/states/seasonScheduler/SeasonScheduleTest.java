package dhl.businessLogicTest.simulationStateMachineTest.states.seasonScheduler;

import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SeasonScheduleTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    ILeagueObjectModel league;
    ISeasonSchedule seasonSchedule;
    IScheduler scheduler;
    MockAbstractFactory mockAbstractFactory;
    SchedulerAbstractFactory schedulerAbstractFactory;

    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        league = model20TeamMocks.getLeagueData();
        seasonSchedule = schedulerAbstractFactory.getSeasonSchedule();
        scheduler = schedulerAbstractFactory.getScheduler();
    }

    @Test
    public void getTeamOneConferenceTest() {
        seasonSchedule.setTeamOneConference(league.getConferences().get(0));
        Assertions.assertNotNull(seasonSchedule.getTeamOneConference());
    }

    @Test
    public void setTeamOneConferenceTest() {
        seasonSchedule.setTeamOneConference(league.getConferences().get(0));
        Assertions.assertEquals("Eastern Conference", seasonSchedule.getTeamOneConference().getConferenceName());
    }

    @Test
    public void getTeamTwoConferenceTest() {
        seasonSchedule.setTeamTwoConference(league.getConferences().get(0));
        Assertions.assertNotNull(seasonSchedule.getTeamTwoConference());
    }

    @Test
    public void setTeamTwoConferenceTest() {
        seasonSchedule.setTeamTwoConference(league.getConferences().get(0));
        Assertions.assertEquals("Eastern Conference", seasonSchedule.getTeamTwoConference().getConferenceName());
    }

    @Test
    public void getTeamOneDivisionTest() {
        seasonSchedule.setTeamOneDivision(league.getConferences().get(0).getDivisions().get(0));
        Assertions.assertEquals("Atlantic Division", seasonSchedule.getTeamOneDivision().getDivisionName());
    }

    @Test
    public void setTeamOneDivisionTest() {
        seasonSchedule.setTeamOneDivision(league.getConferences().get(0).getDivisions().get(0));
        Assertions.assertEquals("Atlantic Division", seasonSchedule.getTeamOneDivision().getDivisionName());
    }

    @Test
    public void getTeamTwoDivisionTest() {
        seasonSchedule.setTeamTwoDivision(league.getConferences().get(0).getDivisions().get(0));
        Assertions.assertEquals("Atlantic Division", seasonSchedule.getTeamTwoDivision().getDivisionName());
    }

    @Test
    public void setTeamTwoDivisionTest() {
        seasonSchedule.setTeamTwoDivision(league.getConferences().get(1).getDivisions().get(1));
        Assertions.assertEquals("Pacific Division", seasonSchedule.getTeamTwoDivision().getDivisionName());
    }

    @Test
    public void getTeamOneTest() {
        seasonSchedule.setTeamOne(league.getConferences().get(0).getDivisions().get(0).getTeams().get(1));
        Assertions.assertEquals("Lightning", seasonSchedule.getTeamOne().getTeamName());
    }

    @Test
    public void getTeamTwoTest() {
        seasonSchedule.setTeamTwo(league.getConferences().get(0).getDivisions().get(0).getTeams().get(2));
        Assertions.assertEquals("Maple", seasonSchedule.getTeamTwo().getTeamName());
    }

    @Test
    public void setTeamOneTest() {
        seasonSchedule.setTeamOne(league.getConferences().get(0).getDivisions().get(0).getTeams().get(1));
        Assertions.assertEquals("Lightning", seasonSchedule.getTeamOne().getTeamName());
    }

    @Test
    public void setTeamTwoTest() {
        seasonSchedule.setTeamTwo(league.getConferences().get(0).getDivisions().get(0).getTeams().get(2));
        Assertions.assertEquals("Maple", seasonSchedule.getTeamTwo().getTeamName());
    }

    @Test
    public void getGameDateTest() {
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);
        LocalDate date = LocalDate.now();
        scheduler.getFullSeasonSchedule().get(0).setGameDate(date);
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().get(0).getGameDate().equals(date));
    }

    @Test
    public void setGameDateTest() {
        scheduler.generateTeamList(league);
        scheduler.generateTeamSchedule(league);
        LocalDate date = LocalDate.now();
        scheduler.getFullSeasonSchedule().get(0).setGameDate(date);
        Assertions.assertTrue(scheduler.getFullSeasonSchedule().get(0).getGameDate().equals(date));
    }

    @Test
    public void isMatchPlayedTest() {
        seasonSchedule.setMatchPlayed(true);
        Assertions.assertTrue(seasonSchedule.isMatchPlayed());
    }

    @Test
    public void isMatchToBePlayedTest() {
        seasonSchedule.setMatchToBePlayed(true);
        Assertions.assertTrue(seasonSchedule.isMatchToBePlayed());
    }

}