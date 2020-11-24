package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.FreeAgentMock;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreeAgentTest {
    IPlayer freeAgent;
    LeagueModelMockAbstractFactory mockFactory;
    FreeAgentMock freeAgentMock;

    @BeforeEach()
    public void initObject() {
        mockFactory = LeagueModelMockAbstractFactory.instance();
        freeAgentMock = mockFactory.createFreeAgentMock();
        freeAgent = freeAgentMock.getPlayer();
    }

    @Test
    public void FreeAgentTest() {
        Assertions.assertEquals("forward", freeAgent.getPosition());
        Assertions.assertEquals("Noah", freeAgent.getPlayerName());
    }

    @Test
    public void checkPlayerNameValidTest() {
        freeAgent = freeAgentMock.getPlayerWithoutName();
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            freeAgent.checkPlayerValid();
        });
        Assertions.assertTrue(error.getMessage().contains("Player name cannot be empty"));
    }

    @Test
    public void checkPlayerPositionValidTest() {
        IPlayer freeAgent = freeAgentMock.getPlayerInvalidPosition();
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            freeAgent.checkPlayerValid();
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Player position must be goalie or forward or defense"));
    }

    @Test
    public void checkPlayerValidTest() throws Exception {
        Assertions.assertTrue(freeAgent.checkPlayerValid());
    }

}
