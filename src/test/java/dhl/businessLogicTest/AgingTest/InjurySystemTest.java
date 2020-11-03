package dhl.businessLogicTest.AgingTest;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.InjurySystem;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.aging.Interface.IInjurySystem;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class InjurySystemTest {
    IGameConfig gameConfig;
    IInjurySystem injurySystem;

    @BeforeEach()
    public void initObject() {
        LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        injurySystem = new InjurySystem();
    }

    @Test
    public void checkTeamInjuryTest() {
        LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();
        IGameConfig gameConfig = leagueMock.getGameConfig();
        ITeam team = leagueMock.getTeamObjectMock();
        injurySystem.checkTeamInjury(gameConfig, team);
        List<IPlayer> playerList = team.getPlayers();
        IPlayer player = playerList.get(0);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void checkIfPlayerInjuredTest() {
        IPlayer player = new Player();
        injurySystem.checkIfPlayerInjured(gameConfig, player);
        Assertions.assertEquals(-1, player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerAlreadyInjuredTest() {
        IPlayer player = new Player();
        player.setPlayerInjuredDays(10);
        injurySystem.checkIfPlayerInjured(gameConfig, player);
        Assertions.assertTrue(injurySystem.checkIfPlayerInjured(gameConfig, player));
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

    @AfterEach()
    public void destroyObject() {
        injurySystem = null;
        gameConfig = null;
    }


}
