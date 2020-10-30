package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.factory.InitializeObjectFactory;
import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.InjurySystem;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class PlayerTest {
    InitializeObjectFactory initObj;
    IPlayer player;
    IPlayerStatistics playerStatistics;
    InjurySystem injuredPlayer;
    IGameConfig gameConfig;

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        player= initObj.createPlayer();
        playerStatistics =new PlayerStatistics(20,10,10,10,10);
        injuredPlayer=new InjurySystem(new Date(),20);
        LeagueObjectModelMocks leagueMock= new LeagueObjectModelMocks();
        gameConfig=leagueMock.getGameConfig();
    }

    @Test
    public void PlayerDefaultConstructorTest(){
        Assertions.assertTrue(player.getPlayerName().isEmpty());
        Assertions.assertEquals("",player.getPosition() );
    }

    @Test
    public void PlayerTest(){
        IPlayer player= new Player("Harry","forward",false,playerStatistics);
        Assertions.assertEquals("forward",player.getPosition() );
        Assertions.assertEquals("Harry",player.getPlayerName() );
        Assertions.assertFalse(player.getCaptain() );
    }

    @Test
    public void getPlayerIdTest(){
        player.setPlayerId(5);
        Assertions.assertEquals(5,player.getPlayerId());
    }

    @Test
    public void setPlayerIdTest(){
        player.setPlayerId(10);
        Assertions.assertEquals(10,player.getPlayerId());
    }

    @Test
    public void getPlayerNameTest(){
        player.setPlayerName("Rick Nash");
        Assertions.assertEquals("Rick Nash",player.getPlayerName());
    }

    @Test
    public void setPlayerNameTest(){
        player.setPlayerName("Nikita Kucherov");
        Assertions.assertEquals("Nikita Kucherov",player.getPlayerName());
    }

    @Test
    public void getPositionTest(){
        player.setPosition("goalie");
        Assertions.assertEquals("goalie",player.getPosition());
    }

    @Test
    public void setPositionTest(){
        player.setPosition("defense");
        Assertions.assertEquals("defense",player.getPosition());
    }

    @Test
    public void setPositionForwardTest(){
        player.setPosition("forward");
        Assertions.assertEquals("forward",player.getPosition());
    }

    @Test
    public void getPositionEmptyTest(){
        Assertions.assertEquals("",player.getPosition());
    }

    @Test
    public void getCaptainTest(){
        player.setCaptain(true);
        Assertions.assertTrue(player.getCaptain());
    }

    @Test
    public void setCaptainTest(){
        player.setCaptain(false);
        Assertions.assertFalse(player.getCaptain());
    }

    @Test
    void setPlayerStatsTest() {
        player.setPlayerStats(playerStatistics);
        IPlayerStatistics playerStatistics=player.getPlayerStats();
        Assertions.assertEquals(20,playerStatistics.getAge());
    }

    @Test
    void setInjurySystemTest() {
        player.setInjurySystem(injuredPlayer);
        IInjurySystem injury=player.getInjurySystem();
        Assertions.assertTrue(injury.isInjured());
    }

    @Test
    public void isPlayerNameEmpty(){
        Assertions.assertTrue(player.isPlayerNameEmpty());
    }

    @Test
    public void isPlayerPositionInvalidTest(){
        player.setPosition("leftCenter");
        Assertions.assertTrue(player.isPlayerPositionInvalid());
    }

    @Test
    public void isPlayerPositionInvalidTes(){
        player.setPosition("forward");
        Assertions.assertFalse(player.isPlayerPositionInvalid());
    }

    @Test
    public void isCaptainValueBooleanTest(){
        Assertions.assertTrue(player.isCaptainValueBoolean());
    }

    @Test
    public void checkPlayerNameValidTest() {
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assertions.assertTrue(error.getMessage().contains("Player name cannot be empty"));
    }

    @Test
    public void checkPlayerPositionValidTest() {
        player.setPlayerName("Noah");
        player.setPosition("leg side");
        Exception errorMsg=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Player position must be goalie or forward or defense"));
    }

    @Test
    public void checkPlayerCaptainValueValidTest() {
        player.setPlayerName("Noah");
        player.setPosition("forward");
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assertions.assertTrue(error.getMessage().contains("Captain value must be true or false"));
    }

    @Test
    public void checkPlayerValidTest() throws Exception{
        IPlayer player= new Player("Noah","forward",true,playerStatistics);
        Assertions.assertTrue(player.checkPlayerValid());
    }

    @Test
    public void isPlayerAlreadyInjuredTest(){
        player.setInjurySystem(injuredPlayer);
        player.checkPlayerInjury(gameConfig,new Date());
        IInjurySystem injurySystem = player.getInjurySystem();
        Assertions.assertTrue(injurySystem.isInjured());
    }

    @Test
    public void checkPlayerInjuryTest(){
        player.checkPlayerInjury(gameConfig,new Date());
        IInjurySystem injury= player.getInjurySystem();
        Assertions.assertTrue(injury.isInjured());
    }

    @Test
    public void getPlayerStrengthTest(){
        player.setPlayerStats(playerStatistics);
        player.setPosition("forward");
        Assertions.assertEquals(25,player.getPlayerStrength());
        player.setPosition("goalie");
        Assertions.assertEquals(20,player.getPlayerStrength());
        player.setPosition("defense");
        Assertions.assertEquals(25,player.getPlayerStrength());
        player.setInjurySystem(injuredPlayer);
        player.setPosition("defense");
        Assertions.assertEquals(12.5,player.getPlayerStrength());
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        player= null;
    }

}
