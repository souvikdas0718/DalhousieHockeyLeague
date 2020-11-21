package dhl.importJsonTest;

import dhl.Mocks.GameConfigMock;
import dhl.inputOutput.importJson.GeneralManagerPersonalityList;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;
import java.util.List;

public class GeneralManagerPersonalityListTest {

    GameConfigMock gameConfigMock;
    GeneralManagerPersonalityList testClassObject;

    @BeforeEach
    public void initObject() {
        gameConfigMock = new GameConfigMock();
        IGameConfig ourGameConfig = gameConfigMock.getGameConfigMock();
        testClassObject = new GeneralManagerPersonalityList(ourGameConfig);
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
