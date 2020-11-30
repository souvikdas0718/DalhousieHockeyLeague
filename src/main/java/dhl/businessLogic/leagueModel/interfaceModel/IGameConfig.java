package dhl.businessLogic.leagueModel.interfaceModel;

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

    String getStatDecayChance();

    String getInjuries();

    String getRandomInjuryChance();

    String getInjuryDaysLow();

    String getInjuryDaysHigh();

    String getTraining();

    String getDaysUntilStatIncreaseCheck();

    String getGameResolver();

    String getRandomWinChance();

    String getSimulation();

    String getPenaltyChance();

    String getGoalChance();

    HashMap<String, Object> getHashMap(String key);

    String getValueFromOurObject(String configChildKey, String ourObjectKey);
}
