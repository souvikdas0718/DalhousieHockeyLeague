package dhl.importJsonTest;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.inputOutput.importJson.GeneralManagerPersonalityList;
import dhl.inputOutput.importJson.GeneralManagerPersonalityListAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneralManagerPersonalityListAbstractTest {

    GeneralManagerPersonalityListAbstract testClassObject;
    LeagueModelMockAbstractFactory leagueMockFactory;
    IGameConfig gameConfig;

    @BeforeEach
    public void initObject(){
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        gameConfig = leagueMockFactory.createGameplayConfig().getGameplayConfig();
        testClassObject = GeneralManagerPersonalityListAbstract.instance(gameConfig);
    }

    @Test
    public void instanceTest(){
        Assertions.assertFalse(testClassObject == null);
        Assertions.assertEquals(testClassObject , GeneralManagerPersonalityListAbstract.instance(gameConfig));
    }

    @Test
    public void setFactoryTest(){
        GeneralManagerPersonalityListAbstract oldObject = testClassObject;

        GeneralManagerPersonalityListAbstract.setFactory(new GeneralManagerPersonalityList(gameConfig));
        testClassObject = GeneralManagerPersonalityList.instance(gameConfig);

        Assertions.assertFalse(oldObject == testClassObject);
    }
}
