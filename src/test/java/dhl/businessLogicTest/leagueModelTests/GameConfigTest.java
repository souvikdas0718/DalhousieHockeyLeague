package dhl.businessLogicTest.leagueModelTests;
;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class GameConfigTest {

    IGameConfig testClassObject;
    LeagueModelMockAbstractFactory mockFactory;

    @BeforeEach
    public void initObject() throws Exception {
        mockFactory = LeagueModelMockAbstractFactory.instance();
        GameplayConfigMock gameConfigMock = mockFactory.createGameplayConfig();
        testClassObject = gameConfigMock.getGameplayConfig();
    }

    @Test
    public void getLossPoint() {
        Assertions.assertEquals("lossPoint", testClassObject.getLossPoint());
    }

    @Test
    public void getRandomTradeOfferChance() {
        Assertions.assertEquals("randomTradeOfferChance", testClassObject.getRandomTradeOfferChance());
    }

    @Test
    public void getMaxPlayersPerTrade() {
        Assertions.assertEquals("maxPlayersPerTrade", testClassObject.getMaxPlayersPerTrade());
    }

    @Test
    public void getRandomAcceptanceChance() {
        Assertions.assertEquals("randomAcceptanceChance", testClassObject.getRandomAcceptanceChance());
    }

    @Test
    public void getTrading() {
        Assertions.assertEquals("trading", testClassObject.getTrading());
    }

    @Test
    public void getAging() {
        Assertions.assertEquals("aging", testClassObject.getAging());
    }

    @Test
    public void getAverageRetirementAge() {
        Assertions.assertEquals("averageRetirementAge", testClassObject.getAverageRetirementAge());
    }

    @Test
    public void getMaximumAge() {
        Assertions.assertEquals("maximumAge", testClassObject.getMaximumAge());
    }

    @Test
    public void getInjuries() {
        Assertions.assertEquals("injuries", testClassObject.getInjuries());
    }

    @Test
    public void getRandomInjuryChance() {
        Assertions.assertEquals("randomInjuryChance", testClassObject.getRandomInjuryChance());
    }

    @Test
    public void getInjuryDaysLow() {
        Assertions.assertEquals("injuryDaysLow", testClassObject.getInjuryDaysLow());
    }

    @Test
    public void getInjuryDaysHigh() {
        Assertions.assertEquals("injuryDaysHigh", testClassObject.getInjuryDaysHigh());
    }

    @Test
    public void getTraining() {
        Assertions.assertEquals("training", testClassObject.getTraining());
    }

    @Test
    public void getDaysUntilStatIncreaseCheckTest() {
        Assertions.assertEquals("daysUntilStatIncreaseCheck", testClassObject.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void getHashMapTest() {
        HashMap<String, Object> tradingObject = testClassObject.getHashMap("trading");
        HashMap<String, Object> agingObject = testClassObject.getHashMap("aging");
        Assertions.assertFalse(tradingObject.isEmpty());
        Assertions.assertTrue(tradingObject.size() == 5);
        Assertions.assertTrue(agingObject.size() == 3);
        Assertions.assertTrue(testClassObject.getHashMap("WrongKey").isEmpty());
    }

    @Test
    public void getValueFromOurObjectTest() {
        String lossPointValue = testClassObject.getValueFromOurObject("trading", "lossPoint");
        int losspoint = Integer.parseInt(lossPointValue);
        Assertions.assertTrue(losspoint > 0);
        Double randomTradeOfferChance = Double.parseDouble(testClassObject.getValueFromOurObject("trading", "randomTradeOfferChance"));
        Assertions.assertTrue(randomTradeOfferChance > 0 );
    }
    @Test
    public void getGameResolverTest() {
        Assertions.assertEquals("gameResolver", testClassObject.getGameResolver());
    }
    @Test
    public void getRandomWinChanceTest() {
        Assertions.assertEquals("randomWinChance", testClassObject.getRandomWinChance());
    }

}
