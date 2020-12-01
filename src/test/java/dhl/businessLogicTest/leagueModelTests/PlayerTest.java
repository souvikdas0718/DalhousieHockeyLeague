package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    LeagueModelMockAbstractFactory factory;
    Player player;
    PlayerMock playerMock;

    @BeforeEach()
    public void initObject() {
        factory = LeagueModelMockAbstractFactory.instance();
        playerMock = factory.createPlayerMock();
        player = (Player) playerMock.getPlayer();
    }

    @Test
    public void PlayerDefaultConstructorTest() {
        LeagueModelAbstractFactory leagueFactory = LeagueModelAbstractFactory.instance();
        player = (Player) leagueFactory.createPlayerDefault();
        String playerName = player.getPlayerName();
        Assertions.assertTrue(playerName.length() == 0);
        Assertions.assertEquals("", player.getPosition());
    }


    @Test
    public void PlayerTest() {
        Assertions.assertEquals("forward", player.getPosition());
        Assertions.assertEquals("Harry", player.getPlayerName());
        Assertions.assertFalse(player.getCaptain());
    }

    @Test
    public void getPlayerNameTest() {
        Assertions.assertEquals("Harry", player.getPlayerName());
    }

    @Test
    public void getPositionTest() {
        player.setPosition("goalie");
        Assertions.assertEquals("goalie", player.getPosition());
    }

    @Test
    public void setPositionTest() {
        player.setPosition("defense");
        Assertions.assertEquals("defense", player.getPosition());
    }

    @Test
    public void setPositionForwardTest() {
        player.setPosition("forward");
        Assertions.assertEquals("forward", player.getPosition());
    }

    @Test
    public void getCaptainTest() {
        player.setCaptain(true);
        Assertions.assertTrue(player.getCaptain());
    }

    @Test
    public void getPlayerStatsTest() {
        IPlayerStatistics playerStatistics = player.getPlayerStats();
        Assertions.assertEquals(20, playerStatistics.getAge());
    }

    @Test
    public void setActive() {
        player.setActive(true);
        Assertions.assertTrue(player.isActive());
    }

    @Test
    void setPlayerInjuredDaysTest() {
        player.setPlayerInjuredDays(10);
        Assertions.assertEquals(10, player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerNameEmpty() {
        player = (Player) playerMock.getPlayerWithoutName();
        Assertions.assertTrue(player.isPlayerNameEmpty());
    }

    @Test
    public void isPlayerPositionInvalidTest() {
        player = (Player) playerMock.getPlayerInvalidPosition();
        Assertions.assertTrue(player.isPlayerPositionInvalid());
    }

    @Test
    public void isPlayerPositionInvalidTes() {
        Assertions.assertFalse(player.isPlayerPositionInvalid());
    }

    @Test
    public void isCaptainValueBooleanTest() {
        player= (Player) playerMock.getInvalidPlayerCaptain();
        Assertions.assertTrue(player.isCaptainValueBoolean());
    }

    @Test
    public void checkPlayerNameValidTest() {
        player = (Player) playerMock.getPlayerWithoutName();
        Assertions.assertTrue(player.isPlayerNameEmpty());
    }

    @Test
    public void checkPlayerPositionValidTest() {
        player = (Player) playerMock.getPlayerInvalidPosition();
        Assertions.assertTrue(player.isPlayerPositionInvalid());
    }

    @Test
    public void checkPlayerPositionInValidTest() {
        LeagueModelAbstractFactory leagueFactory = LeagueModelAbstractFactory.instance();
        player = (Player) leagueFactory.createPlayerDefault();
        Assertions.assertEquals("",player.getPosition());
    }

    @Test
    public void checkPlayerCaptainValueValidTest() {
        player = (Player) playerMock.getInvalidPlayerCaptain();
        Assertions.assertTrue(player.isCaptainValueBoolean());
    }

    @Test
    public void getPlayerStrengthTest() {
        player.setPosition("forward");
        Assertions.assertEquals(25, player.getPlayerStrength());
        player.setPosition("goalie");
        Assertions.assertEquals(20, player.getPlayerStrength());
        player.setPosition("defense");
        Assertions.assertEquals(25, player.getPlayerStrength());
        player.setPlayerInjuredDays(2);
        player.setPosition("defense");
        Assertions.assertEquals(12.5, player.getPlayerStrength());
    }

    @AfterEach()
    public void destroyObject() {
        player = null;
    }

}
