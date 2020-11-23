package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.GeneralManager;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.ManagerMock;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneralManagerTest {
    IGeneralManager generalManager;
    LeagueModelMockAbstractFactory factory;
    ManagerMock managerMock;

    @BeforeEach()
    public void initObject() {
        factory = LeagueModelMockAbstractFactory.instance();
        managerMock = factory.createManagerMock();
        generalManager = managerMock.getManager();
    }

    @Test
    public void ManagerTest() {
        generalManager = managerMock.getManagerWithoutName();
        Assertions.assertEquals("",generalManager.getGeneralManagerName());
    }

    @Test
    public void ManageParameterized() {
        Assertions.assertEquals("Mathew Jacob",generalManager.getGeneralManagerName());
    }

    @AfterEach()
    public void destroyObject() {
        generalManager = null;
    }
}
