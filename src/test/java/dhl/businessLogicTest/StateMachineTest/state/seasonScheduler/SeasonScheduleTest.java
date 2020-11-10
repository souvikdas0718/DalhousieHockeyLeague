package dhl.businessLogicTest.StateMachineTest.state.seasonScheduler;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.Interface.ISchedule;
import dhl.businessLogic.simulationStateMachine.States.seasonScheduler.SeasonSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SeasonScheduleTest {

    @Test
    public void getTeamOneConferenceTest() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamOneConference());
    }

    @Test
    public void setTeamOneConferenceTest() {
        ISchedule seasonSchedule = new SeasonSchedule();

        String generalManager = "generalManager";

        ICoach headCoach = new Coach("Coach", 0.1, 0.2, .5, .9);
        ICoach headCoach2 = new Coach("Coach2", 0.1, 0.2, .5, .8);
        List<ICoach> coaches = new ArrayList<>();
        coaches.add(headCoach);
        coaches.add(headCoach2);
        IPlayerStatistics playerStats = new PlayerStatistics(20, 5, 5, 8, 9);
        IPlayer playersList1 = new Player("playerName", "position", playerStats);
        IPlayer playersList2 = new Player("playerName", "position", playerStats);
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(playersList1);
        playersList.add(playersList2);

        ITeam team1 = new Team("Bruins", generalManager, headCoach, playersList);
        ITeam team2 = new Team("Lightning", generalManager, headCoach, playersList);
        ITeam team3 = new Team("Maple", generalManager, headCoach, playersList);
        ITeam team4 = new Team("Panthers", generalManager, headCoach, playersList);
        ITeam team5 = new Team("Canadiens", generalManager, headCoach, playersList);

        ITeam team6 = new Team("Capitals", generalManager, headCoach, playersList);
        ITeam team7 = new Team("Flyers", generalManager, headCoach, playersList);
        ITeam team8 = new Team("Penguins", generalManager, headCoach, playersList);
        ITeam team9 = new Team("Hurricanes", generalManager, headCoach, playersList);
        ITeam team10 = new Team("BlueJackets", generalManager, headCoach, playersList);

        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);

        IDivision division1 = new Division("Atlantic Division", teamsListDivision1);
        IDivision division2 = new Division("Metropolitan Division", teamsListDivision2);

        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        conference1Divisions.add(division1);
        conference1Divisions.add(division2);

        IConference conference = new Conference("Eastern Conference", conference1Divisions);
        seasonSchedule.setTeamOneConference(conference);
        Assertions.assertEquals("Eastern Conference", seasonSchedule.getTeamOneConference().getConferenceName());
    }

    @Test
    public void getTeamTwoConferenceTest() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamTwoConference());
    }

    @Test
    public void setTeamTwoConferenceTest() {
        ISchedule seasonSchedule = new SeasonSchedule();

        String generalManager = "generalManager";

        ICoach headCoach = new Coach("Coach", 0.1, 0.2, .5, .9);
        ICoach headCoach2 = new Coach("Coach2", 0.1, 0.2, .5, .8);
        List<ICoach> coaches = new ArrayList<>();
        coaches.add(headCoach);
        coaches.add(headCoach2);
        IPlayerStatistics playerStats = new PlayerStatistics(20, 5, 5, 8, 9);
        IPlayer playersList1 = new Player("playerName", "position", playerStats);
        IPlayer playersList2 = new Player("playerName", "position", playerStats);
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(playersList1);
        playersList.add(playersList2);

        ITeam team1 = new Team("Bruins", generalManager, headCoach, playersList);
        ITeam team2 = new Team("Lightning", generalManager, headCoach, playersList);
        ITeam team3 = new Team("Maple", generalManager, headCoach, playersList);
        ITeam team4 = new Team("Panthers", generalManager, headCoach, playersList);
        ITeam team5 = new Team("Canadiens", generalManager, headCoach, playersList);

        ITeam team6 = new Team("Capitals", generalManager, headCoach, playersList);
        ITeam team7 = new Team("Flyers", generalManager, headCoach, playersList);
        ITeam team8 = new Team("Penguins", generalManager, headCoach, playersList);
        ITeam team9 = new Team("Hurricanes", generalManager, headCoach, playersList);
        ITeam team10 = new Team("BlueJackets", generalManager, headCoach, playersList);

        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);

        IDivision division1 = new Division("Atlantic Division", teamsListDivision1);
        IDivision division2 = new Division("Metropolitan Division", teamsListDivision2);

        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        conference1Divisions.add(division1);
        conference1Divisions.add(division2);

        IConference conference = new Conference("Western Conference", conference1Divisions);
        seasonSchedule.setTeamOneConference(conference);
        Assertions.assertEquals("Western Conference", seasonSchedule.getTeamOneConference().getConferenceName());
    }

    @Test
    public void getTeamOneDivision() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamOneDivision());
    }

    @Test
    public void setTeamOneDivision() {
        ISchedule seasonSchedule = new SeasonSchedule();

        String generalManager = "generalManager";

        ICoach headCoach = new Coach("Coach", 0.1, 0.2, .5, .9);
        ICoach headCoach2 = new Coach("Coach2", 0.1, 0.2, .5, .8);
        List<ICoach> coaches = new ArrayList<>();
        coaches.add(headCoach);
        coaches.add(headCoach2);
        IPlayerStatistics playerStats = new PlayerStatistics(20, 5, 5, 8, 9);
        IPlayer playersList1 = new Player("playerName", "position", playerStats);
        IPlayer playersList2 = new Player("playerName", "position", playerStats);
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(playersList1);
        playersList.add(playersList2);

        ITeam team1 = new Team("Bruins", generalManager, headCoach, playersList);
        ITeam team2 = new Team("Lightning", generalManager, headCoach, playersList);
        ITeam team3 = new Team("Maple", generalManager, headCoach, playersList);
        ITeam team4 = new Team("Panthers", generalManager, headCoach, playersList);
        ITeam team5 = new Team("Canadiens", generalManager, headCoach, playersList);

        ITeam team6 = new Team("Capitals", generalManager, headCoach, playersList);
        ITeam team7 = new Team("Flyers", generalManager, headCoach, playersList);
        ITeam team8 = new Team("Penguins", generalManager, headCoach, playersList);
        ITeam team9 = new Team("Hurricanes", generalManager, headCoach, playersList);
        ITeam team10 = new Team("BlueJackets", generalManager, headCoach, playersList);

        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);

        IDivision division1 = new Division("Atlantic Division", teamsListDivision1);
        IDivision division2 = new Division("Metropolitan Division", teamsListDivision2);

        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        conference1Divisions.add(division1);
        conference1Divisions.add(division2);

        IConference conference = new Conference("Western Conference", conference1Divisions);
        seasonSchedule.setTeamOneConference(conference);

        Assertions.assertEquals("Atlantic Division", seasonSchedule.getTeamOneConference().getDivisions().get(0).getDivisionName());
        Assertions.assertEquals("Metropolitan Division", seasonSchedule.getTeamOneConference().getDivisions().get(1).getDivisionName());
    }

    @Test
    public void getTeamTwoDivision() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamTwoDivision());
    }

    @Test
    public void setTeamTwoDivision() {
        ISchedule seasonSchedule = new SeasonSchedule();

        String generalManager = "generalManager";

        ICoach headCoach = new Coach("Coach", 0.1, 0.2, .5, .9);
        ICoach headCoach2 = new Coach("Coach2", 0.1, 0.2, .5, .8);
        List<ICoach> coaches = new ArrayList<>();
        coaches.add(headCoach);
        coaches.add(headCoach2);
        IPlayerStatistics playerStats = new PlayerStatistics(20, 5, 5, 8, 9);
        IPlayer playersList1 = new Player("playerName", "position", playerStats);
        IPlayer playersList2 = new Player("playerName", "position", playerStats);
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(playersList1);
        playersList.add(playersList2);

        ITeam team1 = new Team("Bruins", generalManager, headCoach, playersList);
        ITeam team2 = new Team("Lightning", generalManager, headCoach, playersList);
        ITeam team3 = new Team("Maple", generalManager, headCoach, playersList);
        ITeam team4 = new Team("Panthers", generalManager, headCoach, playersList);
        ITeam team5 = new Team("Canadiens", generalManager, headCoach, playersList);

        ITeam team6 = new Team("Capitals", generalManager, headCoach, playersList);
        ITeam team7 = new Team("Flyers", generalManager, headCoach, playersList);
        ITeam team8 = new Team("Penguins", generalManager, headCoach, playersList);
        ITeam team9 = new Team("Hurricanes", generalManager, headCoach, playersList);
        ITeam team10 = new Team("BlueJackets", generalManager, headCoach, playersList);

        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);

        IDivision division1 = new Division("Atlantic Division", teamsListDivision1);
        IDivision division2 = new Division("Metropolitan Division", teamsListDivision2);

        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        conference1Divisions.add(division1);
        conference1Divisions.add(division2);

        IConference conference = new Conference("Western Conference", conference1Divisions);
        seasonSchedule.setTeamOneConference(conference);

        Assertions.assertEquals("Metropolitan Division", seasonSchedule.getTeamOneConference().getDivisions().get(1).getDivisionName());
    }

    @Test
    public void getTeamOne() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamOne());
    }

    @Test
    public void getTeamTwo() {
        ISchedule seasonSchedule = new SeasonSchedule();
        Assertions.assertNotNull(seasonSchedule.getTeamTwo());
    }

}