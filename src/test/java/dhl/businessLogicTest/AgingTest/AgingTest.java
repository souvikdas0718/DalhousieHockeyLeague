package dhl.businessLogicTest.AgingTest;


import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class AgingTest {

    LeagueObjectModelMocks leagueMock;
    IGameConfig gameConfig;
    Aging aging;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        aging = new Aging(gameConfig);

    }

    @Test
    public void setAverageRetirementAgeTest() {
        Assertions.assertEquals(35, aging.getAverageRetirementAge());
    }


    @Test
    public void setMaximumAgeTest() {
        Assertions.assertEquals(50, aging.getMaximumAge());
    }

    @Test
    public void ageAllPlayerTest() {
        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectMock();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    aging.ageAllPlayers(team.getPlayers());
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player = players.get(0);
                    IPlayerStatistics playerStatistics = player.getPlayerStats();
                    Assertions.assertEquals(26, playerStatistics.getAge());
                }
            }
        }
    }

    @Test
    public void checkLikelihoodOfRetirementTest() {
        Assertions.assertTrue(aging.checkLikelihoodOfRetirement(100));
        Assertions.assertFalse(aging.checkLikelihoodOfRetirement(0));
    }

    @Test
    public void selectPlayersToRetireMaxAgeTest() {
        Map<String, List<IPlayer>> playersSelectedToRetire;
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 20, 20, 20, 20);
        players.add(new Player("PlayerOne", "forward", true, playerStatistics1));

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ITeam team = new Team("Mock Team", "Mock Manager", headCoach, players);
        List<ITeam> teams = new ArrayList<>();
        teams.add(team);
        playersSelectedToRetire = aging.selectPlayersToRetire(team);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectPlayersToRetireGreaterThanAvgTest() {
        Random random = aging.getRandomGenerator();
        random.setSeed(1);
        Map<String, List<IPlayer>> playersSelectedToRetire ;
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics2 = new PlayerStatistics(36, 20, 20, 20, 20);
        players.add(new Player("PlayerTwo", "forward", true, playerStatistics2));

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ITeam team = new Team("Mock Team", "Mock Manager", headCoach, players);
        List<ITeam> teams = new ArrayList<>();
        teams.add(team);
        playersSelectedToRetire = aging.selectPlayersToRetire(team);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectPlayersToRetireLessThanAvgTest() {
        Random random = aging.getRandomGenerator();
        random.setSeed(5000);
        Map<String, List<IPlayer>> playersSelectedToRetire;
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics3 = new PlayerStatistics(34, 20, 20, 20, 20);
        players.add(new Player("PlayerThree", "forward", true, playerStatistics3));

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ITeam team = new Team("Mock Team", "Mock Manager", headCoach, players);
        List<ITeam> teams = new ArrayList<>();
        teams.add(team);
        playersSelectedToRetire = aging.selectPlayersToRetire(team);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectFreeAgentsToRetireTest() {
        List<IPlayer> agentsSelectedToRetire ;
        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 20, 20, 20, 20);
        freeAgents.add(new FreeAgent("PlayerOne", "forward", playerStatistics1));


        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectMock();
        leagueObjectModel = new LeagueObjectModel(leagueObjectModel.getLeagueName(), leagueObjectModel.getConferences(), freeAgents, new ArrayList<>(), new ArrayList<>(), gameConfig);
        agentsSelectedToRetire = aging.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());

        Assertions.assertEquals(1, agentsSelectedToRetire.size());
    }

    @AfterEach()
    public void destroyObject() {
        leagueMock = null;
        gameConfig = null;
        aging = null;

    }

}
