package dhl.tradingTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.importJson.GameConfig;
import dhl.importJson.ImportJsonFile;
import dhl.trading.TradeConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TradeConfigTest {

    TradeConfig testClassObject;
    JsonFilePathMock filePathMock;
    GameConfig gameConfig;
    HashMap mockHashMap;
    @BeforeEach
    public void initObject() throws Exception {
        filePathMock = new JsonFilePathMock();
        ImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        gameConfig = new GameConfig(importJsonFile.getJsonObject());
        testClassObject = new TradeConfig(gameConfig);

        mockHashMap = new HashMap();
        mockHashMap.put("lossPoint",(long) 9);
        mockHashMap.put("maxPlayersPerTrade", (long) 3);
        mockHashMap.put("randomAcceptanceChance", (double) 0.52);
        mockHashMap.put("randomTradeOfferChance", (double) 0.41);
    }

    @Test
    public void getTradingConfigTest(){
        Assertions.assertFalse(testClassObject.getTradingConfig().isEmpty());
    }

    @Test
    public void setTradingConfigTest(){
        testClassObject.setTradingConfig(mockHashMap);
        Assertions.assertTrue(testClassObject.getLossPoint() == 9);
        Assertions.assertTrue(testClassObject.getMaxPlayersPerTrade() == 3);
        Assertions.assertTrue(testClassObject.getRandomAcceptanceChance() == 0.52);
        Assertions.assertTrue(testClassObject.getRandomTradeOfferChance() == 0.41);
    }

    @Test
    public void getRandomAcceptanceChanceTest(){
        testClassObject.setRandomAcceptanceChance( (double) mockHashMap.get("randomAcceptanceChance"));
        Assertions.assertTrue(testClassObject.getRandomAcceptanceChance()==0.52);
    }

    @Test
    public void setRandomAcceptanceChance(){
        testClassObject.setRandomAcceptanceChance( (double) mockHashMap.get("randomAcceptanceChance"));
        Assertions.assertTrue(testClassObject.getRandomAcceptanceChance()==0.52);
    }

    @Test
    public void getLossPointTest() {
        testClassObject.setLossPoint( (long) mockHashMap.get("lossPoint"));
        Assertions.assertTrue(testClassObject.getLossPoint()==9);
    }

    @Test
    public void setLossPointTest() {
        testClassObject.setLossPoint( (long) mockHashMap.get("lossPoint"));
        Assertions.assertTrue(testClassObject.getLossPoint()==9);
    }

    @Test
    public void getMaxPlayersPerTrade() {
        testClassObject.setMaxPlayersPerTrade( (long) mockHashMap.get("maxPlayersPerTrade"));
        Assertions.assertTrue(testClassObject.getMaxPlayersPerTrade()==3);
    }

    @Test
    public void setMaxPlayersPerTrade() {
        testClassObject.setMaxPlayersPerTrade( (long) mockHashMap.get("maxPlayersPerTrade"));
        Assertions.assertTrue(testClassObject.getMaxPlayersPerTrade()==3);
    }

    @Test
    public void getRandomTradeOfferChance() {
        testClassObject.setRandomTradeOfferChance( (double) mockHashMap.get("randomTradeOfferChance"));
        Assertions.assertTrue(testClassObject.getRandomTradeOfferChance()==0.41);
    }

    @Test
    public void setRandomTradeOfferChance() {
        testClassObject.setRandomTradeOfferChance( (double) mockHashMap.get("randomTradeOfferChance"));
        Assertions.assertTrue(testClassObject.getRandomTradeOfferChance()==0.41);
    }

}
