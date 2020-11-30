package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.PlayerDraft;
import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerDraftAbstractTest {

    PlayerDraftAbstract testClassObject = null;
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach
    public void initObject() {
        leagueFactory = LeagueModelAbstractFactory.instance();
        testClassObject = PlayerDraftAbstract.instance();
    }

    @Test
    public void instanceTest(){
        PlayerDraftAbstract testObject = PlayerDraftAbstract.instance();
        Assertions.assertTrue(testObject == testClassObject);
    }

    @Test
    public void setFactoryTest(){
        PlayerDraftAbstract oldInstance = PlayerDraftAbstract.instance();
        PlayerDraftAbstract newInstance = new PlayerDraft();
        testClassObject.setFactory(newInstance);
        testClassObject = PlayerDraftAbstract.instance();

        Assertions.assertFalse(testClassObject == oldInstance);
        Assertions.assertTrue(testClassObject == newInstance);
    }
}
