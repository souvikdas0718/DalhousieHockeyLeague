package dhl.leagueModelTests;

import dhl.leagueModel.GeneralManager;
import dhl.leagueModel.interfaceModel.IGeneralManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneralManagerTest {
    IGeneralManager generalManager;

    @BeforeEach()
    public void initObject() {
        generalManager = new GeneralManager();
    }

    @Test
    public void ManagerTest() {
        Assertions.assertEquals(generalManager.getGeneralManagerName(), "");
    }

    @Test
    public void ManageParameterized() {
        generalManager = new GeneralManager("Mathew John");
        Assertions.assertEquals(generalManager.getGeneralManagerName(), "Mathew John");
    }

    @AfterEach()
    public void destroyObject() {
        generalManager = null;
    }
}
