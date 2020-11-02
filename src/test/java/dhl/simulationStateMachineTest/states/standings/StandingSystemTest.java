package dhl.simulationStateMachineTest.states.standings;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.IStandings;
import dhl.simulationStateMachine.states.standings.StandingSystem;
import dhl.simulationStateMachine.states.standings.Standings;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
public class StandingSystemTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    ICoach coach;
    List<IPlayer> players;
    String manager;
    @BeforeEach
    public void initObject(){
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        coach = mockLeagueObjectModel.getSingleCoach();
        players=mockLeagueObjectModel.get20FreeAgentArrayMock();
        manager="Harry";
    }

    @Test
    public void updateWinningStandingsTest() {

        ITeam team = new Team("Maverick",manager,coach,players);

        ITeam team2 = new Team("Denver",manager,coach,players);

        ITeam team3 = new Team("Vine",manager,coach,players);

        ITeam team4 = new Team("Loki",manager,coach,players);

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
        ITeam team = new Team("Maverick",manager,coach,players);

        ITeam team2 = new Team("Denver",manager,coach,players);

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
}
