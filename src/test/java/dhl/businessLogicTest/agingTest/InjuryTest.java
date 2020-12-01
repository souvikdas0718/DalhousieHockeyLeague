package dhl.businessLogicTest.agingTest;

import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class InjuryTest {
    IGameConfig gameConfig;
    Injury injury;
    ITeam team;
    LeagueModelAbstractFactory leagueFactory;
    AgingAbstractFactory agingFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    TeamMock teamMock;

    @BeforeEach()
    public void initObject() {
        leagueFactory = LeagueModelAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        teamMock = leagueMockFactory.createTeamMock();

        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        gameConfig = gameplayConfigMock.getAgingGameConfig();
        agingFactory = AgingAbstractFactory.instance();
        injury = (Injury) agingFactory.createInjury();
        team = teamMock.getTeam();
    }

    @Test
    public void checkTeamInjuryTest() {
        injury.checkTeamInjury(gameConfig, team);
        List<IPlayer> playerList = team.getPlayers();
        IPlayer player = playerList.get(0);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void checkIfPlayerInjuredTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(10, 10, 10, 10);
        playerStatistics.setAge(20);
        IPlayer player = leagueFactory.createPlayer("Harry", "forward", false, playerStatistics);
        ITeam team = teamMock.getTeam();
        injury.checkIfPlayerInjured(gameConfig, player, team);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerAlreadyInjuredTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(10, 10, 10, 10);
        IPlayer player = leagueFactory.createPlayer("Harry", "forward", false, playerStatistics);
        player.setPlayerInjuredDays(10);
        injury.checkIfPlayerInjured(gameConfig, player, team);
        Assertions.assertTrue(injury.checkIfPlayerInjured(gameConfig, player, team));
    }

    @Test
    public void healInjuredPlayersTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(10, 10, 10, 10);
        IPlayer player = leagueFactory.createPlayer("Harry", "forward", false, playerStatistics);
        player.setPlayerInjuredDays(10);
        injury.healInjuredPlayers(player);
        Assertions.assertEquals(9, player.getPlayerInjuredDays());
    }

    @Test
    public void InjuryHealedPlayersTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(10, 10, 10, 10);
        IPlayer player = leagueFactory.createPlayer("Harry", "forward", false, playerStatistics);
        player.setPlayerInjuredDays(0);
        injury.healInjuredPlayers(player);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void swapInjuredPlayerTest() {
        List<IPlayer> playersInTeam = teamMock.getTeamPlayers();
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        Team testTeam = (Team) leagueFactory.createTeam("Ontario", manager, leagueFactory.createCoachDefault(), playersInTeam);
        IPlayer player = playersInTeam.get(0);
        injury.swapInjuredPlayer(player, testTeam);
        Assertions.assertFalse(player.isActive());
    }

    @Test
    public void swapRecoveredPlayerTest() {
        List<IPlayer> playersInTeam = teamMock.getTeamPlayers();
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        Team testTeam = (Team) leagueFactory.createTeam("Ontario", manager, leagueFactory.createCoachDefault(), playersInTeam);
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(20, 20, 20, 20);
        IPlayer player = leagueFactory.createPlayer("Rehab", "forward", false, playerStatistics);
        injury.swapRecoveredPlayer(player, testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @Test
    public void healInjuredPlayersInTeamTest() {
        List<IPlayer> playersInTeam = teamMock.getTeamPlayers();
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        Team testTeam = (Team) leagueFactory.createTeam("Ontario", manager, leagueFactory.createCoachDefault(), playersInTeam);
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(20, 20, 20, 20);
        IPlayer player = leagueFactory.createPlayer("Rehab", "forward", false, playerStatistics);
        player.setPlayerInjuredDays(0);
        player.setActive(false);
        injury.healInjuredPlayersInTeam(player, testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @AfterEach()
    public void destroyObject() {
        injury = null;
        gameConfig = null;
    }


}
