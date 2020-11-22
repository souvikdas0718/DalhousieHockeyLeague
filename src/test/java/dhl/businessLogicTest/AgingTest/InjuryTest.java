package dhl.businessLogicTest.AgingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class InjuryTest {
    IGameConfig gameConfig;
    Injury injury;
    LeagueObjectModelMocks leagueMock;
    ITeam team;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        injury = new Injury();
        team = leagueMock.getTeamObjectMock();
    }

    @Test
    public void checkTeamInjuryTest() {
        LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();
        IGameConfig gameConfig = leagueMock.getGameConfig();
        injury.checkTeamInjury(gameConfig, team);
        List<IPlayer> playerList = team.getPlayers();
        IPlayer player = playerList.get(0);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void checkIfPlayerInjuredTest() {
        IPlayer player = new Player();
        ITeam team = leagueMock.getTeamObjectMock();
        injury.checkIfPlayerInjured(gameConfig, player,team);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerAlreadyInjuredTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(10);
        injury.checkIfPlayerInjured(gameConfig, player,team);
        Assertions.assertTrue(injury.checkIfPlayerInjured(gameConfig, player,team));
    }

    @Test
    public void healInjuredPlayersTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(10);
        injury.healInjuredPlayers(player);
        Assertions.assertEquals(9, player.getPlayerInjuredDays());
    }

    @Test
    public void InjuryHealedPlayersTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(0);
        injury.healInjuredPlayers(player);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void swapInjuredPlayerTest(){
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        IGeneralManager manager = new GeneralManager("Mathew", "normal");
        Team testTeam = new Team("Ontario", manager, new Coach(),playersInTeam );
        IPlayer player = playersInTeam.get(0);
        injury.swapInjuredPlayer(player,testTeam);
        Assertions.assertFalse(player.isActive());
    }

    @Test
    public void swapRecoveredPlayerTest(){
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        IGeneralManager manager = new GeneralManager("Mathew", "normal");
        Team testTeam = new Team("Ontario", manager, new Coach(),playersInTeam );
        IPlayerStatistics playerStatistics = new PlayerStatistics(21,20,20,20,20);
        IPlayer player = new Player("Rehab","forward",false,playerStatistics);
        injury.swapRecoveredPlayer(player,testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @Test
    public void healInjuredPlayersInTeamTest() {
        List<IPlayer> playersInTeam = leagueMock.getTeamPlayers();
        IGeneralManager manager = new GeneralManager("Mathew", "normal");
        Team testTeam = new Team("Ontario", manager, new Coach(),playersInTeam );
        IPlayerStatistics playerStatistics = new PlayerStatistics(21,20,20,20,20);
        IPlayer player = new Player("Rehab","forward",false,playerStatistics);
        player.setPlayerInjuredDays(0);
        player.setActive(false);
        injury.healInjuredPlayersInTeam(player,testTeam);
        Assertions.assertTrue(player.isActive());
    }

    @AfterEach()
    public void destroyObject() {
        injury = null;
        gameConfig = null;
        leagueMock = null;
    }


}
