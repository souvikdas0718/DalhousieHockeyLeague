package dhl.businessLogicTest.simulationStateMachineTest.states.standings;


import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.RegularSeasonStandingListMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StandingSystemTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    RegularSeasonStandingListMocks regularSeasonStanding;
    ICoach coach;
    List<IPlayer> players;
    String manager;
    MockAbstractFactory mockAbstractFactory;
    StandingsAbstractFactory standingsAbstractFactory;
    IStandingSystem standingSystem;

    @BeforeEach
    public void initObject() {
        mockAbstractFactory = MockAbstractFactory.instance();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        regularSeasonStanding = mockAbstractFactory.getRegularSeasonStandingListMock();
        coach = mockLeagueObjectModel.getSingleCoach();
        players = mockLeagueObjectModel.get20FreeAgentArrayMock();
        manager = "Harry";
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        standingSystem = standingsAbstractFactory.getStandingSystem();
    }

    @Test
    public void getStandingsListTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        standingSystem.setStandingsList(standingsList);
        Assertions.assertTrue(standingSystem.getStandingsList().size() == 20);
    }

    @Test
    public void setStandingsListTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
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
        standingSystem.setStandingsList(regularSeasonStanding.generalSeasonStandings());
        standingSystem.updateWinningStandings(standingSystem.getStandingsList().get(0).getTeam());

        for (IStandings standing : standingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(standingSystem.getStandingsList().get(0).getTeam().getTeamName())) {
                Assert.assertEquals(standing.getPoints(), 12);
                Assert.assertEquals(standing.getWins(), 6);
                Assert.assertEquals(standing.getGamesPlayed(), 10);
            }
        }
    }

    @Test
    public void updateLosingStandingsTest() {

        standingSystem.setStandingsList(regularSeasonStanding.generalSeasonStandings());
        standingSystem.updateLosingStandings(standingSystem.getStandingsList().get(0).getTeam());

        for (IStandings standing : standingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(standingSystem.getStandingsList().get(0).getTeam().getTeamName())) {
                Assert.assertEquals(standing.getPoints(), 10);
                Assert.assertEquals(standing.getLoss(), 5);
                Assert.assertEquals(standing.getGamesPlayed(), 10);
            }
        }

    }


    @Test
    public void rankGeneratorSamePointsTest() {
        List<IStandings> standings = new ArrayList<>();

        IStandings standings1 = standingsAbstractFactory.getStandings();
        standings1.setPoints(10);
        standings1.setWins(4);
        standings1.setLoss(4);

        IStandings standings2 = standingsAbstractFactory.getStandings();
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
        List<IStandings> standings = new ArrayList<>();

        IStandings standings1 = standingsAbstractFactory.getStandings();
        standings1.setPoints(8);
        standings1.setWins(5);
        standings1.setLoss(5);

        IStandings standings2 = standingsAbstractFactory.getStandings();
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
        List<IStandings> standings = new ArrayList<>();

        IStandings standings1 = standingsAbstractFactory.getStandings();
        standings1.setPoints(10);
        standings1.setWins(5);
        standings1.setLoss(4);

        IStandings standings2 = standingsAbstractFactory.getStandings();
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

        standingSystem.setStandingsList(standingsList);
        standingsList = standingSystem.leagueRanking();

        Assert.assertEquals(standingsList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(standingsList.get(0).getPoints(), 94);

        Assert.assertEquals(standingsList.get(1).getTeam().getTeamName(), "Capitals");
        Assert.assertEquals(standingsList.get(1).getPoints(), 92);
    }

    @Test
    public void conferenceRankingTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        List<IStandings> conferenceTeamList = standingSystem.conferenceRanking(standingsList.get(0).getTeamConference(), standingsList);

        Assert.assertEquals(conferenceTeamList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(conferenceTeamList.get(0).getPoints(), 94);

        Assert.assertEquals(conferenceTeamList.get(9).getTeam().getTeamName(), "Canadiens");
        Assert.assertEquals(conferenceTeamList.get(9).getPoints(), 70);
    }

    @Test
    public void divisionRankingTest() {
        List<IStandings> standingsList = model20TeamMocks.getGeneralStandings();
        List<IStandings> divisionTeamList = standingSystem.divisionRanking(standingsList.get(0).getTeamDivision(), standingsList);

        Assert.assertEquals(divisionTeamList.get(0).getTeam().getTeamName(), "Bruins");
        Assert.assertEquals(divisionTeamList.get(0).getPoints(), 94);

        Assert.assertEquals(divisionTeamList.get(4).getTeam().getTeamName(), "Canadiens");
        Assert.assertEquals(divisionTeamList.get(4).getPoints(), 70);

    }

    @Test
    public void createStandingsTest() {
        standingSystem.createStandings(model20TeamMocks.getLeagueData());
        Assertions.assertNotNull(standingSystem.getStandingsList());
    }
}

