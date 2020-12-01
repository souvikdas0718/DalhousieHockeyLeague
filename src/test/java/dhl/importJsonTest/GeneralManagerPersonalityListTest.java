package dhl.importJsonTest;

import dhl.mocks.GameConfigMock;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;

import dhl.inputOutput.importJson.GeneralManagerPersonalityListAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;

public class GeneralManagerPersonalityListTest {

    GameConfigMock gameConfigMock;
    GeneralManagerPersonalityListAbstract testClassObject;

    @BeforeEach
    public void initObject() {
        gameConfigMock = new GameConfigMock();
        IGameConfig ourGameConfig = gameConfigMock.getGameConfigMock();
        testClassObject = GeneralManagerPersonalityListAbstract.instance(ourGameConfig);
    }

    @Test
    public void getGeneralManagerPersonalityListTest(){
        String wrongPersonality = "randomWords";
        String rightPersonality= "gambler";

        Dictionary generalManagerPersonalityDict = testClassObject.getGeneralManagerPersonalityList();

        Assertions.assertFalse(generalManagerPersonalityDict.isEmpty());
        Assertions.assertFalse( generalManagerPersonalityDict.get(rightPersonality) == null);
        Assertions.assertTrue( generalManagerPersonalityDict.get(wrongPersonality) == null);
    }
}
