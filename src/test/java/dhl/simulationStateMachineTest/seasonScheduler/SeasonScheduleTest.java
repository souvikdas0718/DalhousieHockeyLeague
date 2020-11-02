package dhl.simulationStateMachineTest.seasonScheduler;

import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.states.seasonScheduler.SeasonSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeasonScheduleTest {

    @Test
    public void getTeamOneConferenceTest() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamOneConference());
    }

//    @Test
//    public void setTeamOneConferenceTest() {
//        ISchedule seasonSchedule = new SeasonSchedule();
//        IConference conference = new Conference();
//        conference.setConferenceName("Eastern Conference");
//        seasonSchedule.setTeamOneConference(conference);
//        Assertions.assertEquals("Eastern Conference", seasonSchedule.getTeamOneConference().getConferenceName());
//    }

//    @Test
//    public void getTeamTwoConferenceTest() {
//        ISchedule seasonSchedule = new SeasonSchedule();
//        Assertions.assertNotNull(seasonSchedule.getTeamTwoConference());
//    }

//    @Test
//    public void setTeamTwoConferenceTest() {
//        ISchedule seasonSchedule = new SeasonSchedule();
//        IConference conference = new Conference();
//        conference.setConferenceName("Western Conference");
//        seasonSchedule.setTeamTwoConference(conference);
//        Assertions.assertEquals("Western Conference", seasonSchedule.getTeamTwoConference().getConferenceName());
//    }

//    @Test
//    public void getTeamOneDivision() {
//        return null;
//    }
//
//    @Test
//    public void setTeamOneDivision(IDivision teamOneDivision) {
//
//    }
//
//    @Test
//    public void getTeamTwoDivision() {
//        return null;
//    }
//
//    @Test
//    public void setTeamTwoDivision(IDivision teamTwoDivision) {
//
//    }
//
//    @Test
//    public void getTeamOne() {
//        return null;
//    }
//
//    @Test
//    public void setTeamOne(ITeam teamOne) {
//
//    }
//
//    @Test
//    public void getTeamTwo() {
//        return null;
//    }
//
//    @Test
//    public void setTeamTwo(ITeam teamTwo) {
//
//    }
//
//    @Test
//    public void getGameDate() {
//        return null;
//    }
//
//    @Test
//    public void setGameDate(LocalDate gameDate) {
//
//    }
}
