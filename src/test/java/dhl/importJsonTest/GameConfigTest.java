package dhl.importJsonTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.importJson.GameConfig;
import dhl.importJson.ImportJsonFile;
import dhl.importJson.Interface.IGameConfig;
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
}
