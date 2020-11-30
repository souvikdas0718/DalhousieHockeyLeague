package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.GameSimulationAbstractFactory;
import dhl.businessLogic.gameSimulation.GameSimulationAlgorithm;
import dhl.businessLogic.gameSimulation.IGameSimulationAlgorithm;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameSimulationAlgorithmTest {

    LeagueModelMockAbstractFactory leagueModelMockAbstractFactory;
    GameSimulationAbstractFactory factory;
    IGameSimulationAlgorithm igameSimulationAlgorithm;
    GameSimulationAlgorithm gameSimulationAlgorithm;
    ITeam teamOne;
    ITeam teamTwo;

    @BeforeEach()
    public void initObject() {
        leagueModelMockAbstractFactory = LeagueModelMockAbstractFactory.instance();
        factory = GameSimulationAbstractFactory.instance();
        igameSimulationAlgorithm = factory.createGameSimulationAlgorithm();
        gameSimulationAlgorithm = new GameSimulationAlgorithm();
        teamOne = leagueModelMockAbstractFactory.createTeamMock().getTeamOneForGameSimulation();
        teamTwo = leagueModelMockAbstractFactory.createTeamMock().getTeamTwoForGameSimulation();
    }

    @Test
    public void getResultOfGameTest(){
        HashMap<String, Integer> mapResult = igameSimulationAlgorithm.getResultOfGame(teamOne, teamTwo);
        Assertions.assertTrue(mapResult.get("Shots")>0);
    }

    @Test
    public void createTeamTest(){
        TeamMock teamMock = leagueModelMockAbstractFactory.createTeamMock();
        gameSimulationAlgorithm.createTeam(teamOne.getPlayers(), teamTwo.getPlayers());
        Boolean shotHappened = false;
        Integer shotsA =gameSimulationAlgorithm.getShotsA();
        Integer shotsB =gameSimulationAlgorithm.getShotsB();
        if (shotsA>0 || shotsB>0){
            shotHappened = true;
        }

        Assertions.assertTrue(shotHappened);
    }

    @Test
    public void incrementShotsGoalsSavesPenaltiesTest(){
        GameSimulationMockAbstractFactory factory = GameSimulationMockAbstractFactory.instance();
        MapStatsMock mock = factory.createMapStatsMock();
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferForwardTeamOne());
        Assertions.assertTrue(gameSimulationAlgorithm.getGoalsA().equals(1));
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferSavingGoalieTeamOne());
        Assertions.assertTrue(gameSimulationAlgorithm.getSavesB().equals(1));
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferDefenceTeamTwo());
        Assertions.assertTrue(gameSimulationAlgorithm.getSavesB().equals(2));
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferForwardTeamTwo());
        Assertions.assertTrue(gameSimulationAlgorithm.getGoalsB().equals(1));
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferForwardCheckingTeamTwo());
        Assertions.assertTrue(gameSimulationAlgorithm.getSavesA().equals(1));
        gameSimulationAlgorithm.incrementShotsGoalsSavesPenalties(mock.mapStatsPreferForwardSavingTeamTwo());
        Assertions.assertTrue(gameSimulationAlgorithm.getSavesA().equals(2));
    }

    @Test
    public void getMaxStatTest(){
        Integer maxStat = gameSimulationAlgorithm.getMaxStat(teamOne.getPlayers(),"shooting");
        Integer minStat = 0;
        Assertions.assertTrue(maxStat>minStat);
    }

    @Test
    public void getPlayersTest(){
        List<Integer> listOldPlayers = new ArrayList<>();
        List<IPlayer> forwardPlayers = gameSimulationAlgorithm.getPlayers("forward", teamOne.getPlayers() ,listOldPlayers);
        Assertions.assertEquals(3,forwardPlayers.size());
        List<IPlayer> defensePlayers = gameSimulationAlgorithm.getPlayers("defense", teamOne.getPlayers() ,listOldPlayers);
        Assertions.assertEquals(2,defensePlayers.size());
    }

    @Test
    public void getRandomNumbersTest(){
        List<Integer> randomNumberList1 = gameSimulationAlgorithm.getRandomNumbers(new ArrayList<>(),3,"forward");
        List<Integer> randomNumberList2 = gameSimulationAlgorithm.getRandomNumbers(randomNumberList1,3,"forward");
        Assertions.assertNotEquals(randomNumberList1,randomNumberList2);
    }

    @Test
    public void generateRandomNumberTest(){
        List<Integer> exclude = new ArrayList<>();
        exclude.add(9);
        gameSimulationAlgorithm.generateRandomNumber(6,exclude);
        Integer randomNumber = gameSimulationAlgorithm.getgRandomNumber();
        Assertions.assertNotEquals(9,randomNumber);
    }

    @Test
    public void checkWinnerTest(){
        Integer winner = gameSimulationAlgorithm.checkWinner(1,2);
        Integer winningTeam = 2;
        Assertions.assertEquals(winningTeam, winner);
    }

    @Test
    public void getPlayerCountTest(){
        Integer playercount = 0;
        Integer expected = 0;

        playercount = gameSimulationAlgorithm.getPlayerCount("forward", 1);
        expected = 3;
        Assertions.assertEquals(expected, playercount);

        playercount = gameSimulationAlgorithm.getPlayerCount("defense", 1);
        expected = 1;
        Assertions.assertEquals(expected, playercount);

        playercount = gameSimulationAlgorithm.getPlayerCount("defense", 2);
        expected = 2;
        Assertions.assertEquals(expected, playercount);
    }
}
