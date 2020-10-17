package dhl.leagueModelTests;

import dhl.leagueModel.FreeAgent;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IFreeAgent;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreeAgentTest {
    IFreeAgent freeAgent;
    IPlayerStatistics playerStatistics;

    @BeforeEach()
    public void initObject(){
        freeAgent= new FreeAgent();
        playerStatistics =new PlayerStatistics(20,10,10,10,0);
    }

    @Test
    public void FreeAgentDefaultConstructorTest(){
        Assertions.assertTrue(freeAgent.getPlayerName().isEmpty());
        Assertions.assertEquals("",freeAgent.getPosition() );
    }

    @Test
    public void FreeAgentTest(){
        IFreeAgent freeAgent= new FreeAgent("Harry","forward",playerStatistics);
        Assertions.assertEquals("forward",freeAgent.getPosition() );
        Assertions.assertEquals("Harry",freeAgent.getPlayerName() );
    }

    @Test
    public void getPlayerIdTest(){
        freeAgent.setPlayerId(5);
        Assertions.assertEquals(5,freeAgent.getPlayerId());
    }

    @Test
    public void setPlayerIdTest(){
        freeAgent.setPlayerId(10);
        Assertions.assertEquals(10,freeAgent.getPlayerId());
    }

    @Test
    public void getPlayerNameTest(){
        freeAgent.setPlayerName("Rick Nash");
        Assertions.assertEquals("Rick Nash",freeAgent.getPlayerName());
    }

    @Test
    public void setPlayerNameTest(){
        freeAgent.setPlayerName("Nikita Kucherov");
        Assertions.assertEquals("Nikita Kucherov",freeAgent.getPlayerName());
    }

    @Test
    public void getPositionTest(){
        freeAgent.setPosition("goalie");
        Assertions.assertEquals("goalie",freeAgent.getPosition());
    }

    @Test
    public void setPositionTest(){
        freeAgent.setPosition("defense");
        Assertions.assertEquals("defense",freeAgent.getPosition());
    }

    @Test
    public void setPositionForwardTest(){
        freeAgent.setPosition("forward");
        Assertions.assertEquals("forward",freeAgent.getPosition());
    }

    @Test
    public void getPositionEmptyTest(){
        Assertions.assertEquals("",freeAgent.getPosition());
    }

    @Test
    void setPlayerStatsTest() {
        freeAgent.setPlayerStats(playerStatistics);
        Assertions.assertEquals(20,freeAgent.getPlayerStats().getAge());
    }
}
