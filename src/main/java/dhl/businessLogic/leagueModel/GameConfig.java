package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GameConfig implements IGameConfig {
    private static final String trading = "trading";
    private static final String lossPoint = "lossPoint";
    private static final String randomTradeOfferChance = "randomTradeOfferChance";
    private static final String maxPlayersPerTrade = "maxPlayersPerTrade";
    private static final String randomAcceptanceChance = "randomAcceptanceChance";
    private static final String gmTable = "gmTable";
    private static final String aging = "aging";
    private static final String averageRetirementAge = "averageRetirementAge";
    private static final String maximumAge = "maximumAge";
    private static final String injuries = "injuries";
    private static final String randomInjuryChance = "randomInjuryChance";
    private static final String injuryDaysLow = "injuryDaysLow";
    private static final String injuryDaysHigh = "injuryDaysHigh";
    private static final String training = "training";
    private static final String daysUntilStatIncreaseCheck = "daysUntilStatIncreaseCheck";
    private static final String gameResolver = "gameResolver";
    private static final String randomWinChance = "randomWinChance";
    private static final String statDecayChance = "statDecayChance";
    private static final String simulation = "simulation";
    private static final String penaltyChance = "penaltyChance";
    private static final String goalChance = "goalChance";
    JSONObject gameConfigJson;

    public GameConfig(JSONObject jsonObject) {
        gameConfigJson = jsonObject;
        gameConfigJson = (JSONObject) gameConfigJson.get("gameplayConfig");
    }

    public String getLossPoint() {
        return lossPoint;
    }

    public String getRandomTradeOfferChance() {
        return randomTradeOfferChance;
    }

    public String getMaxPlayersPerTrade() {
        return maxPlayersPerTrade;
    }

    public String getRandomAcceptanceChance() {
        return randomAcceptanceChance;
    }

    public String getTrading() {
        return trading;
    }

    public String getAging() {
        return aging;
    }

    public String getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public String getMaximumAge() {
        return maximumAge;
    }

    public String getStatDecayChance() {
        return statDecayChance;
    }

    public String getInjuries() {
        return injuries;
    }

    public String getRandomInjuryChance() {
        return randomInjuryChance;
    }

    public String getInjuryDaysLow() {
        return injuryDaysLow;
    }

    public String getInjuryDaysHigh() {
        return injuryDaysHigh;
    }

    public String getTraining() {
        return training;
    }

    public String getDaysUntilStatIncreaseCheck() {
        return daysUntilStatIncreaseCheck;
    }

    public String getGameResolver() {
        return gameResolver;
    }

    public String getRandomWinChance() {
        return randomWinChance;
    }

    public String getSimulation() {
        return simulation;
    }

    public String getPenaltyChance() {
        return penaltyChance;
    }

    public String getGoalChance() {
        return goalChance;
    }

    public HashMap<String, Object> getHashMap(String key) {
        HashMap<String, Object> mapToReturn = new HashMap<>();
        JSONObject subObject = (JSONObject) gameConfigJson.get(key);
        if (isJsonValuePresent(subObject)) {
            for (Object subObjectKey : subObject.keySet()) {
                if (isJsonValuePresent(subObjectKey)) {
                    mapToReturn.put(subObjectKey.toString(), subObject.get(subObjectKey.toString()));
                }
            }
        }
        return mapToReturn;
    }

    public String getValueFromOurObject(String configChildKey, String ourObjectKey) {
        Map<String, Object> gameConfigChildObject = getHashMap(configChildKey);
        return String.valueOf(gameConfigChildObject.get(ourObjectKey));
    }

    public boolean isJsonValuePresent(Object jsonValue) {
        if (jsonValue == null || jsonValue.toString().isEmpty()) {
            return false;
        }
        return true;
    }
}
