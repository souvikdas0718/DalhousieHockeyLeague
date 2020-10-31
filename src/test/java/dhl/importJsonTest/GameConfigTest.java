package dhl.importJsonTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class GameConfigTest {

    IGameConfig testClassObject;
    JsonFilePathMock filePathMock;
    @BeforeEach
    public void initObject() throws Exception {
        filePathMock = new JsonFilePathMock();
        ImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        testClassObject = new GameConfig(importJsonFile.getJsonObject());
    }

    @Test
    public void getHashMapTest(){
        HashMap tradingObject = testClassObject.getHashMap("trading");
        HashMap agingObject = testClassObject.getHashMap("aging");
        Assertions.assertFalse(tradingObject.isEmpty());
        Assertions.assertTrue(tradingObject.size() == 4);
        Assertions.assertTrue(agingObject.size() ==2);
        Assertions.assertTrue(testClassObject.getHashMap("WrongKey").isEmpty());
    }

    @Test
    public void getValueFromOurObjectTest(){
        String lossPointValue = testClassObject.getValueFromOurObject("trading","lossPoint");
        int losspoint = Integer.parseInt(lossPointValue);
        Assertions.assertTrue(losspoint == 8);
        Double randomTradeOfferChance = Double.parseDouble(testClassObject.getValueFromOurObject("trading","randomTradeOfferChance"));
        Assertions.assertTrue(randomTradeOfferChance == 0.05);
    }
}
