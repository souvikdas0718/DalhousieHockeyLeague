package dhl.InputOutput.importJson.Interface;

import java.util.HashMap;

public interface IGameConfig {

    String getLossPoint();

    String getRandomTradeOfferChance();

    String getMaxPlayersPerTrade();

    String getRandomAcceptanceChance();

    String getTrading();

    String getAging();

    String getAverageRetirementAge();

    String getMaximumAge();

    String getInjuries();

    String getRandomInjuryChance();

    String getInjuryDaysLow();

    String getInjuryDaysHigh() ;

    String getTraining();

    String getDaysUntilStatIncreaseCheck();

    HashMap getHashMap(String key);

    String getValueFromOurObject(String configChildKey , String ourObjectKey);

    String getRandomWinChance();

    String getGameResolver();
}
