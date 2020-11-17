package dhl.businessLogicTest.AgingTest;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.InjurySystem;
import dhl.businessLogic.leagueModel.Coach;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class InjurySystemTest {
    IGameConfig gameConfig;
    InjurySystem injurySystem;
    LeagueObjectModelMocks leagueMock;
    ITeam team;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        injurySystem = new InjurySystem();
        team = leagueMock.getTeamObjectMock();
    }

    @Test
    public void checkTeamInjuryTest() {
        LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();
        IGameConfig gameConfig = leagueMock.getGameConfig();
        injurySystem.checkTeamInjury(gameConfig, team);
        List<IPlayer> playerList = team.getPlayers();
        IPlayer player = playerList.get(0);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void checkIfPlayerInjuredTest() {
        IPlayer player = new Player();
        ITeam team = leagueMock.getTeamObjectMock();
        injurySystem.checkIfPlayerInjured(gameConfig, player,team);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerAlreadyInjuredTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(10);
        injurySystem.checkIfPlayerInjured(gameConfig, player,team);
        Assertions.assertTrue(injurySystem.checkIfPlayerInjured(gameConfig, player,team));
    }

    @Test
    public void healInjuredPlayersTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(10);
        injurySystem.healInjuredPlayers(player);
        Assertions.assertEquals(9, player.getPlayerInjuredDays());
    }

    @Test
    public void InjuryHealedPlayersTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(0);
        injurySystem.healInjuredPlayers(player);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void swapInjuredPlayerTest(){
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        Team testTeam = new Team("Ontario", "Mathew", new Coach(),playersInTeam );
        IPlayer player = playersInTeam.get(0);
        injurySystem.swapInjuredPlayer(player,testTeam);
        Assertions.assertFalse(player.isActive());
    }

    @Test
    public void swapRecoveredPlayerTest(){
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        Team testTeam = new Team("Ontario", "Mathew", new Coach(),playersInTeam );
        IPlayerStatistics playerStatistics = new PlayerStatistics(21,20,20,20,20);
        IPlayer player = new Player("Rehab","forward",false,playerStatistics);
        injurySystem.swapRecoveredPlayer(player,testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @Test
    public void healInjuredPlayersInTeamTest() {
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        Team testTeam = new Team("Ontario", "Mathew", new Coach(),playersInTeam );
        IPlayerStatistics playerStatistics = new PlayerStatistics(21,20,20,20,20);
        IPlayer player = new Player("Rehab","forward",false,playerStatistics);
        player.setPlayerInjuredDays(0);
        player.setActive(false);
        injurySystem.healInjuredPlayersInTeam(player,testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @AfterEach()
    public void destroyObject() {
        injurySystem = null;
        gameConfig = null;
        leagueMock = null;
    }


}
