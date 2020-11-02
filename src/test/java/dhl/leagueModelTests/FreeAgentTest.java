package dhl.leagueModelTests;

import dhl.leagueModel.FreeAgent;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreeAgentTest {
    IPlayer freeAgent;
    IPlayerStatistics playerStatistics;

    @BeforeEach()
    public void initObject() {
        freeAgent = new FreeAgent();
        playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
    }

    @Test
    public void FreeAgentDefaultConstructorTest() {
        String name = freeAgent.getPlayerName();
        Assertions.assertTrue(name.length() == 0);
        Assertions.assertEquals("", freeAgent.getPosition());
    }


    @Test
    public void FreeAgentTest() {
        IPlayer freeAgent = new FreeAgent("Harry", "forward", playerStatistics);
        Assertions.assertEquals("forward", freeAgent.getPosition());
        Assertions.assertEquals("Harry", freeAgent.getPlayerName());
    }

    @Test
    public void checkPlayerNameValidTest() {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            freeAgent.checkPlayerValid();
        });
        Assertions.assertTrue(error.getMessage().contains("Player name cannot be empty"));
    }

    @Test
    public void checkPlayerPositionValidTest() {
        IPlayer freeAgent = new FreeAgent("Noah", "leg side", playerStatistics);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            freeAgent.checkPlayerValid();
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Player position must be goalie or forward or defense"));
    }


    @Test
    public void checkPlayerValidTest() throws Exception {
        IPlayer freeAgent = new FreeAgent("Noah", "forward", playerStatistics);
        Assertions.assertTrue(freeAgent.checkPlayerValid());
    }

    @Test
    public void getPositionEmptyTest() {
        Assertions.assertEquals("", freeAgent.getPosition());
    }

}
