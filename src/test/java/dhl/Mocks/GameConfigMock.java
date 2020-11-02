package dhl.Mocks;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.Interface.IGameConfig;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class GameConfigMock {

    IGameConfig gameConfig;

    public GameConfigMock(){
        JSONObject gameConfigJson = new JSONObject();

        gameConfigJson.put("trading" , getTradingJsonObject());
        JSONObject mainJson = new JSONObject();
        mainJson.put("gameplayConfig" , gameConfigJson);

        gameConfig = new GameConfig(mainJson);
    }
    public JSONObject getTradingJsonObject(){
        JSONObject tradingJson = new JSONObject();
        tradingJson.put("lossPoint", (long) 3);
        tradingJson.put("randomTradeOfferChance", (double) 0.0);
        tradingJson.put("maxPlayersPerTrade", (long) 5);
        tradingJson.put("randomAcceptanceChance", (double) 0.0);
        return tradingJson;
    }
    public IGameConfig getGameConfigMock(){
        return gameConfig;
    }


}
