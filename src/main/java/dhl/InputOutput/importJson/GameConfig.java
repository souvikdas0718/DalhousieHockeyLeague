package dhl.InputOutput.importJson;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import org.json.simple.JSONObject;
import java.util.HashMap;

public class GameConfig implements IGameConfig {

    JSONObject ourJsonObject;
    private String trading = "trading";
    private String lossPoint = "lossPoint";
    private String randomTradeOfferChance = "randomTradeOfferChance";
    private String maxPlayersPerTrade = "maxPlayersPerTrade";
    private String randomAcceptanceChance = "randomAcceptanceChance";
    private String aging = "aging";
    private String averageRetirementAge = "averageRetirementAge";
    private String maximumAge = "maximumAge";
    private String injuries = "injuries";
    private String randomInjuryChance = "randomInjuryChance";
    private String injuryDaysLow = "injuryDaysLow";
    private String injuryDaysHigh = "injuryDaysHigh";
    private String training = "training";
    private String daysUntilStatIncreaseCheck = "daysUntilStatIncreaseCheck";
    private String gameResolver="gameResolver";
    private String randomWinChance="randomWinChance";

    public GameConfig(JSONObject jsonObject){
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
    public HashMap getHashMap(String key){
        HashMap mapToReturn = new HashMap();
        JSONObject subObject = (JSONObject) ourJsonObject.get(key);
        if (subObject != null){
            for (Object subObjectkey:subObject.keySet()){
                if (subObjectkey!= null){
                    if (subObject.get(subObjectkey.toString())!= null){
                        mapToReturn.put(subObjectkey.toString() ,subObject.get(subObjectkey.toString()) );
                    }
                }
            }
        }
        return mapToReturn;
    }
    @Override
    public String getValueFromOurObject(String configChildKey , String ourObjectKey) {
        HashMap gameConfigChildObject = getHashMap(configChildKey);
        String valueToReturn = String.valueOf(gameConfigChildObject.get(ourObjectKey));
        return valueToReturn;

    }

}
