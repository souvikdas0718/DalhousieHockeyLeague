package dhl.businessLogicTest.StateMachineTest.state.standings;


import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.Interface.IStandings;
import dhl.businessLogic.simulationStateMachine.States.standings.StandingSystem;
import dhl.businessLogic.simulationStateMachine.States.standings.Standings;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StandingSystemTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    ICoach coach;
    List<IPlayer> players;
    String manager;

    @BeforeEach
    public void initObject() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        coach = mockLeagueObjectModel.getSingleCoach();
        players = mockLeagueObjectModel.get20FreeAgentArrayMock();
        manager = "Harry";
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
    }

    @Test
    public void getStandingsListTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        IStandingSystem standingSystem = new StandingSystem();
        standingSystem.setStandingsList(standingsList);
        Assertions.assertTrue(standingSystem.getStandingsList().size() == 20);
    }

    @Test
    public void setStandingsListTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        IStandingSystem standingSystem = new StandingSystem();
        standingSystem.setStandingsList(standingsList);
        Assertions.assertTrue(standingSystem.getStandingsList().size() == 20);
    }

    @Test
    public void StandingSystemTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        Assertions.assertFalse(standingsList.isEmpty());
    }

    @Test
    public void updateWinningStandingsTest() {

        ITeam team = new Team("Maverick", manager, coach, players);

        ITeam team2 = new Team("Denver", manager, coach, players);

        ITeam team3 = new Team("Vine", manager, coach, players);

        ITeam team4 = new Team("Loki", manager, coach, players);

        StandingSystem standingSystem = new StandingSystem();
        ArrayList<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setTeam(team);
        standings1.setPoints(10);
        standings1.setWins(5);
        standings1.setLoss(4);
        standings1.setGamesPlayed(9);

        IStandings standings2 = new Standings();
        standings2.setTeam(team2);
        standings2.setPoints(10);
        standings2.setWins(5);
        standings2.setLoss(5);
        standings2.setGamesPlayed(10);

        IStandings standings3 = new Standings();
        standings3.setTeam(team3);
        standings3.setPoints(8);
        standings3.setWins(4);
        standings3.setLoss(5);
        standings3.setGamesPlayed(9);

        IStandings standings4 = new Standings();
        standings4.setTeam(team4);
        standings4.setPoints(8);
        standings4.setWins(4);
        standings4.setLoss(5);
        standings4.setGamesPlayed(9);

        standings.add(standings1);
        standings.add(standings2);
        standings.add(standings3);
        standings.add(standings4);

        for (IStandings standing : standings) {
            if (standing.getTeam().getTeamName().equals(team4.getTeamName())) {
                standing.setPoints(standing.getPoints() + 2);
                standing.setWins(standing.getWins() + 1);
                standing.setGamesPlayed(standing.getGamesPlayed() + 1);
            }
        }

        standingSystem.setStandingsList(standings);
        Assert.assertEquals(standings.get(3).getPoints(), 10);
        Assert.assertEquals(standings.get(3).getWins(), 5);
        Assert.assertEquals(standings.get(3).getGamesPlayed(), 10);
    }

    @Test
    public void updateLosingStandingsTest() {
        ITeam team = new Team("Maverick", manager, coach, players);

        ITeam team2 = new Team("Denver", manager, coach, players);

        StandingSystem standingSystem = new StandingSystem();
        ArrayList<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setTeam(team);
        standings1.setPoints(10);
        standings1.setWins(5);
        standings1.setLoss(4);
        standings1.setGamesPlayed(9);

        IStandings standings2 = new Standings();
        standings2.setTeam(team2);
        standings2.setPoints(10);
        standings2.setWins(5);
        standings2.setLoss(5);
        standings2.setGamesPlayed(10);

        standings.add(standings1);
        standings.add(standings2);

        for (IStandings standings3 : standings) {
            if (standings3.getTeam().getTeamName().equals(team.getTeamName())) {
                standings3.setLoss(standings3.getLoss() + 1);
                standings3.setGamesPlayed(standings3.getGamesPlayed() + 1);
            }
        }

        standingSystem.setStandingsList(standings);
        Assert.assertEquals(standings.get(0).getLoss(), 5);
        Assert.assertEquals(standings.get(0).getGamesPlayed(), 10);
    }


    @Test
    public void rankGeneratorSamePointsTest() {
        StandingSystem standingSystem = new StandingSystem();
        ArrayList<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setPoints(10);
        standings1.setWins(4);
        standings1.setLoss(4);

        IStandings standings2 = new Standings();
        standings2.setPoints(10);
        standings2.setWins(5);
        standings2.setLoss(5);

        standings.add(standings1);
        standings.add(standings2);

        standingSystem.setStandingsList(standings);
        standingSystem.rankGenerator(standings);
        Assert.assertEquals(standings.get(0), standings2);
        Assert.assertEquals(standings.get(1), standings1);
    }

    @Test
    public void rankGeneratorDifferentPointsTest() {
        StandingSystem standingSystem = new StandingSystem();
        ArrayList<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setPoints(8);
        standings1.setWins(5);
        standings1.setLoss(5);

        IStandings standings2 = new Standings();
        standings2.setPoints(10);
        standings2.setWins(4);
        standings2.setLoss(4);

        standings.add(standings1);
        standings.add(standings2);

        standingSystem.setStandingsList(standings);
        standingSystem.rankGenerator(standings);
        Assert.assertEquals(standings.get(0), standings2);
        Assert.assertEquals(standings.get(1), standings1);
    }

    @Test
    public void rankGeneratorSameWinsTest() {
        StandingSystem standingSystem = new StandingSystem();
        ArrayList<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setPoints(10);
        standings1.setWins(5);
        standings1.setLoss(4);

        IStandings standings2 = new Standings();
        standings2.setPoints(10);
        standings2.setWins(5);
        standings2.setLoss(5);

        standings.add(standings1);
        standings.add(standings2);

        standingSystem.setStandingsList(standings);
        standingSystem.rankGenerator(standings);
        Assert.assertEquals(standings.get(0), standings2);
        Assert.assertEquals(standings.get(1), standings1);
    }

    @Test
    public void leagueRankingTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        IStandingSystem standingSystem = new StandingSystem();

        standingSystem.rankGenerator(standingsList);

        Assert.assertEquals(standingsList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(standingsList.get(0).getPoints(), 94);

        Assert.assertEquals(standingsList.get(19).getTeam().getTeamName(), "Canadiens");
        Assert.assertEquals(standingsList.get(19).getPoints(), 70);

    }

    @Test
    public void conferenceRankingTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        List<IStandings> conferenceTeamList = new ArrayList<>();
        IStandingSystem standingSystem = new StandingSystem();

        for (IStandings standings : standingsList) {
            if (standings.getTeamConference().getConferenceName().equals("Eastern Conference")) {
                conferenceTeamList.add(standings);
            }
        }

        standingSystem.rankGenerator(conferenceTeamList);

        Assert.assertEquals(conferenceTeamList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(conferenceTeamList.get(0).getPoints(), 94);

        Assert.assertEquals(conferenceTeamList.get(9).getTeam().getTeamName(), "Canadiens");
        Assert.assertEquals(conferenceTeamList.get(9).getPoints(), 70);

    }

    @Test
    public void divisionRankingTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        List<IStandings> divisionTeamList = new ArrayList<>();
        IStandingSystem standingSystem = new StandingSystem();

        for (IStandings standings : standingsList) {
            if (standings.getTeamDivision().getDivisionName().equals("Atlantic Division")) {
                divisionTeamList.add(standings);
            }
        }

        standingSystem.rankGenerator(divisionTeamList);

        Assert.assertEquals(divisionTeamList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(divisionTeamList.get(0).getPoints(), 94);

        Assert.assertEquals(divisionTeamList.get(4).getTeam().getTeamName(), "Canadiens");
        Assert.assertEquals(divisionTeamList.get(4).getPoints(), 70);

    }
}

