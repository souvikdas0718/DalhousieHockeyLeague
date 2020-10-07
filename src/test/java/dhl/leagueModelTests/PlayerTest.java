package dhl.leagueModelTests;

import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerPosition;
import dhl.leagueModel.interfaceModel.IPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {

    InitializeObjectFactory initObj;
    IPlayer player;
    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        player= initObj.createPlayer();
    }

    @Test
    public void PlayerDefaultConstructorTest(){
        Assertions.assertTrue(player.getPlayerName().isEmpty());
        Assertions.assertEquals("",player.getPosition() );
    }

    @Test
    public void PlayerTest(){
        IPlayer player= new Player("Harry","forward",false);
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
        Assertions.assertFalse(player.isCaptainValueBoolean());
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
        player.setPlayerName("Noah");
        player.setPosition("forward");
        player.setCaptain(true);
        Assertions.assertTrue(player.checkPlayerValid());
    }


    @AfterEach()
    public void destroyObject(){
        initObj = null;
        player= null;
    }


}
