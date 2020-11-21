package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class GameConfig implements IGameConfig {

    JSONObject ourJsonObject;
    private static final String trading = "trading";
    private static final String lossPoint = "lossPoint";
    private static final String randomTradeOfferChance = "randomTradeOfferChance";
    private static final String maxPlayersPerTrade = "maxPlayersPerTrade";
    private static final String randomAcceptanceChance = "randomAcceptanceChance";
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

    public GameConfig(JSONObject jsonObject) {
        ourJsonObject = jsonObject;
        ourJsonObject = (JSONObject) ourJsonObject.get("gameplayConfig");
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

    @Override
    public HashMap<String, Object> getHashMap(String key) {
        HashMap<String, Object> mapToReturn = new HashMap();
        JSONObject subObject = (JSONObject) ourJsonObject.get(key);
        if (subObject != null) {
            for (Object subObjectkey : subObject.keySet()) {
                if (subObjectkey != null) {
                    if (subObject.get(subObjectkey.toString()) != null) {
                        mapToReturn.put(subObjectkey.toString(), subObject.get(subObjectkey.toString()));
                    }
                }
            }
        }
        return mapToReturn;
    }

    @Override
    public String getValueFromOurObject(String configChildKey, String ourObjectKey) {
        HashMap<String, Object> gameConfigChildObject = getHashMap(configChildKey);
        String valueToReturn = String.valueOf(gameConfigChildObject.get(ourObjectKey));
        return valueToReturn;

    }

}
