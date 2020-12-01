package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
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
    public void FreeAgentDefaultTest() {
        LeagueModelAbstractFactory leagueFactory = LeagueModelAbstractFactory.instance();
        freeAgent = leagueFactory.createFreeAgentDefault();
        Assertions.assertEquals("", freeAgent.getPlayerName());
    }

    @Test
    public void FreeAgentTest() {
        Assertions.assertEquals("forward", freeAgent.getPosition());
        Assertions.assertEquals("Noah", freeAgent.getPlayerName());
    }

    @Test
    public void checkPlayerNameValidTest() {
        freeAgent = freeAgentMock.getPlayerWithoutName();
        Assertions.assertTrue(freeAgent.isPlayerNameEmpty());
    }

    @Test
    public void checkPlayerPositionValidTest() {
        IPlayer freeAgent = freeAgentMock.getPlayerInvalidPosition();
        Assertions.assertTrue( freeAgent.isCaptainValueBoolean());
    }

    @Test
    public void checkPlayerValidTest() throws Exception {
        Assertions.assertFalse(freeAgent.isPlayerNameEmpty());
    }

}
