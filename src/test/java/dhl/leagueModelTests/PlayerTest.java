package dhl.leagueModelTests;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.factory.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    InitializeObjectFactory initObj;
    Player player;
    IPlayerStatistics playerStatistics;
    IGameConfig gameConfig;

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        playerStatistics =new PlayerStatistics(20,10,10,10,10);
        player= new Player("Harry","forward",false,playerStatistics);
        LeagueObjectModelMocks leagueMock= new LeagueObjectModelMocks();
        gameConfig=leagueMock.getGameConfig();
    }

    @Test
    public void PlayerDefaultConstructorTest(){
        player= new Player();
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
    public void getPlayerNameTest(){
        Assertions.assertEquals("Harry",player.getPlayerName());
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
        player= new Player();
        Assertions.assertEquals("",player.getPosition());
    }

    @Test
    public void getCaptainTest(){
        player= new Player("Harry","forward",true,playerStatistics);
        Assertions.assertTrue(player.getCaptain());
    }

    @Test
    public void getPlayerStatsTest(){
        IPlayerStatistics playerStatistics=player.getPlayerStats();
        Assertions.assertEquals(20,playerStatistics.getAge());
    }

    @Test
    void setPlayerInjuredDaysTest() {
        player.setPlayerInjuredDays(10);
        Assertions.assertEquals(10,player.getPlayerInjuredDays());
    }

    @Test
    public void isPlayerNameEmpty(){
        player=new Player();
        Assertions.assertTrue(player.isPlayerNameEmpty());
    }

    @Test
    public void isPlayerPositionInvalidTest(){
        player=new Player("Harry","leg side",false,playerStatistics);
        Assertions.assertTrue(player.isPlayerPositionInvalid());
    }

    @Test
    public void isPlayerPositionInvalidTes(){
        player.setPosition("forward");
        Assertions.assertFalse(player.isPlayerPositionInvalid());
    }

    @Test
    public void isCaptainValueBooleanTest(){
        player= new Player();
        Assertions.assertTrue(player.isCaptainValueBoolean());
    }

    @Test
    public void checkPlayerNameValidTest() {
        player=new Player();
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assertions.assertTrue(error.getMessage().contains("Player name cannot be empty"));
    }

    @Test
    public void checkPlayerPositionValidTest() {
        player=new Player("Harry","leg side",false,playerStatistics);
        Exception errorMsg=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Player position must be goalie or forward or defense"));
    }

    @Test
    public void checkPlayerCaptainValueValidTest() {
        player= new Player("Noah","forward",null,playerStatistics);
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
    public void getPlayerStrengthTest(){
        player.setPosition("forward");
        Assertions.assertEquals(25,player.getPlayerStrength());
        player.setPosition("goalie");
        Assertions.assertEquals(20,player.getPlayerStrength());
        player.setPosition("defense");
        Assertions.assertEquals(25,player.getPlayerStrength());
        player.setPlayerInjuredDays(2);
        player.setPosition("defense");
        Assertions.assertEquals(12.5,player.getPlayerStrength());
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        player= null;
    }

}
