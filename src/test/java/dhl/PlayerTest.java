package dhl;

import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.PlayerPosition;
import dhl.leagueModel.interfaceModel.IPlayer;
import org.junit.Assert;
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
        Assert.assertTrue(player.getPlayerName().isEmpty());
        Assert.assertTrue(player.getTeamName().isEmpty());
        Assert.assertEquals("",player.getPosition() );
    }
    @Test
    public void getPlayerIdTest(){
        player.setPlayerId(5);
        Assert.assertEquals(5,player.getPlayerId());
    }
    @Test
    public void setPlayerIdTest(){
        player.setPlayerId(10);
        Assert.assertEquals(10,player.getPlayerId());
    }

    @Test
    public void getPlayerNameTest(){
        player.setPlayerName("Rick Nash");
        Assert.assertEquals("Rick Nash",player.getPlayerName());
    }
    @Test
    public void setPlayerNameTest(){
        player.setPlayerName("Nikita Kucherov");
        Assert.assertEquals("Nikita Kucherov",player.getPlayerName());
    }

    @Test
    public void getPositionTest(){
        player.setPosition("goalie");
        Assert.assertEquals("goalie",player.getPosition());
    }
    @Test
    public void setPositionTest(){
        player.setPosition("defence");
        Assert.assertEquals("defence",player.getPosition());
    }
    @Test
    public void setPositionForwardTest(){
        player.setPosition("forward");
        Assert.assertEquals("forward",player.getPosition());
    }
    @Test
    public void getPositionEmptyTest(){
        Assert.assertEquals("",player.getPosition());
    }

    @Test
    public void getTeamNameTest(){
        player.setTeamName("Dallas Stars");
        Assert.assertEquals("Dallas Stars",player.getTeamName());

    }
    @Test
    public void setTeamNameTest(){
        player.setTeamName("Toronto Maple Leaves");
        Assert.assertEquals("Toronto Maple Leaves",player.getTeamName());
    }

    @Test
    public void getCaptainTest(){
        player.setCaptain(true);
        Assert.assertTrue(player.getCaptain());

    }
    @Test
    public void setCaptainTest(){
        player.setCaptain(false);
        Assert.assertFalse(player.getCaptain());
    }

    @Test
    public void isPlayerNameEmpty(){
        Assert.assertTrue(player.isPlayerNameEmpty());
    }

    @Test
    public void isPlayerPositionInvalidTest(){
        player.setPosition("leftCenter");
        Assert.assertTrue(player.isPlayerPositionInvalid());
    }
    @Test
    public void isPlayerPositionInvalidTes(){
        player.setPosition("forward");
        Assert.assertFalse(player.isPlayerPositionInvalid());
    }
    @Test
    public void isCaptainValueBooleanTest(){
        Assert.assertFalse(player.isCaptainValueBoolean());
    }
    @Test
    public void checkPlayerNameValidTest() {
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assert.assertTrue(error.getMessage().contains("Player name cannot be empty"));
    }
    @Test
    public void checkPlayerPositionValidTest() {
        player.setPlayerName("Noah");
        player.setPosition("leg side");
        Exception errorMsg=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assert.assertTrue(errorMsg.getMessage().contains("Player position must be goalie or forward or defence"));
    }
    @Test
    public void checkPlayerCaptainValueValidTest() {
        player.setPlayerName("Noah");
        player.setPosition("forward");
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            player.checkPlayerValid();
        });
        Assert.assertTrue(error.getMessage().contains("Captain value must be true or false"));
    }
    @Test
    public void checkPlayerValidTest() throws Exception{
        player.setPlayerName("Noah");
        player.setPosition("forward");
        player.setCaptain(true);
        Assert.assertTrue(player.checkPlayerValid());
    }


    @AfterEach()
    public void destroyObject(){
        initObj = null;
        player= null;
    }


}
