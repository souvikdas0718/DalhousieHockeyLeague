package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.GameSimulationAbstractFactory;
import dhl.businessLogic.gameSimulation.IGameSimulation;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameSimulationTest {

    @Test
    public void simulateGameTest(){
        GameSimulationAbstractFactory factory = GameSimulationAbstractFactory.instance();
        IGameSimulation gameSimulation = factory.createGameSimulation();
        LeagueModelMockAbstractFactory leagueModelMockAbstractFactory = LeagueModelMockAbstractFactory.instance();
        ITeam teamOne = leagueModelMockAbstractFactory.createTeamMock().getTeamOneForGameSimulation();
        ITeam teamTwo = leagueModelMockAbstractFactory.createTeamMock().getTeamTwoForGameSimulation();
        gameSimulation.simulateGame(teamOne, teamTwo);
        Assertions.assertNotNull(gameSimulation.getWinner());
    }
}
