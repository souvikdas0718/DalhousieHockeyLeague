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

    IGameConfig gameConfig;
    LeagueModelMockAbstractFactory mockFactory;

    @BeforeEach
    public void initObject()  {
        mockFactory = LeagueModelMockAbstractFactory.instance();
        GameplayConfigMock gameConfigMock = mockFactory.createGameplayConfig();
        gameConfig = gameConfigMock.getGameplayConfig();
    }

    @Test
    public void getLossPoint() {
        Assertions.assertEquals("lossPoint", gameConfig.getLossPoint());
    }

    @Test
    public void getRandomTradeOfferChance() {
        Assertions.assertEquals("randomTradeOfferChance", gameConfig.getRandomTradeOfferChance());
    }

    @Test
    public void getMaxPlayersPerTrade() {
        Assertions.assertEquals("maxPlayersPerTrade", gameConfig.getMaxPlayersPerTrade());
    }

    @Test
    public void getRandomAcceptanceChance() {
        Assertions.assertEquals("randomAcceptanceChance", gameConfig.getRandomAcceptanceChance());
    }

    @Test
    public void getTrading() {
        Assertions.assertEquals("trading", gameConfig.getTrading());
    }

    @Test
    public void getAging() {
        Assertions.assertEquals("aging", gameConfig.getAging());
    }

    @Test
    public void getAverageRetirementAge() {
        Assertions.assertEquals("averageRetirementAge", gameConfig.getAverageRetirementAge());
    }

    @Test
    public void getMaximumAge() {
        Assertions.assertEquals("maximumAge", gameConfig.getMaximumAge());
    }

    @Test
    public void getInjuries() {
        Assertions.assertEquals("injuries", gameConfig.getInjuries());
    }

    @Test
    public void getRandomInjuryChance() {
        Assertions.assertEquals("randomInjuryChance", gameConfig.getRandomInjuryChance());
    }

    @Test
    public void getInjuryDaysLow() {
        Assertions.assertEquals("injuryDaysLow", gameConfig.getInjuryDaysLow());
    }

    @Test
    public void getInjuryDaysHigh() {
        Assertions.assertEquals("injuryDaysHigh", gameConfig.getInjuryDaysHigh());
    }

    @Test
    public void getTraining() {
        Assertions.assertEquals("training", gameConfig.getTraining());
    }

    @Test
    public void getDaysUntilStatIncreaseCheckTest() {
        Assertions.assertEquals("daysUntilStatIncreaseCheck", gameConfig.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void getSimulation() {
        Assertions.assertEquals("simulation", gameConfig.getSimulation());
    }

    @Test
    public void getPenaltyChance() {
        Assertions.assertEquals("penaltyChance", gameConfig.getPenaltyChance());
    }

    @Test
    public void getGoalChance() {
        Assertions.assertEquals("goalChance", gameConfig.getGoalChance());
    }

    @Test
    public void getStatDecayChance() {
        Assertions.assertEquals("statDecayChance", gameConfig.getStatDecayChance());
    }

    @Test
    public void getHashMapTest() {
        HashMap<String, Object> tradingObject = gameConfig.getHashMap("trading");
        HashMap<String, Object> agingObject = gameConfig.getHashMap("aging");
        Assertions.assertFalse(tradingObject.isEmpty());
        Assertions.assertEquals(5,tradingObject.size());
        Assertions.assertEquals(3,agingObject.size());
        Assertions.assertTrue(gameConfig.getHashMap("WrongKey").isEmpty());
    }

    @Test
    public void getValueFromOurObjectTest() {
        String lossPointValue = gameConfig.getValueFromOurObject("trading", "lossPoint");
        int lossPoint = Integer.parseInt(lossPointValue);
        Assertions.assertTrue(lossPoint > 0);
        double randomTradeOfferChance = Double.parseDouble(gameConfig.getValueFromOurObject("trading", "randomTradeOfferChance"));
        Assertions.assertTrue(randomTradeOfferChance > 0 );
    }
    @Test
    public void getGameResolverTest() {
        Assertions.assertEquals("gameResolver", gameConfig.getGameResolver());
    }
    @Test
    public void getRandomWinChanceTest() {
        Assertions.assertEquals("randomWinChance", gameConfig.getRandomWinChance());
    }

}
