package dhl.Mocks;

import dhl.importJson.GameConfig;
import dhl.importJson.Interface.IGameConfig;
import dhl.trading.Interface.ITradeConfig;
import dhl.trading.TradeConfig;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class TradeConfigMock {

    IGameConfig gameConfigWithJustTrading;
    ITradeConfig tradeConfig;

    public TradeConfigMock(){
        JSONObject tradingJson = new JSONObject();
        tradingJson.put("lossPoint", (long) 3);
        tradingJson.put("randomTradeOfferChance", (double) 0.5);
        tradingJson.put("maxPlayersPerTrade", (long) 2);
        tradingJson.put("randomAcceptanceChance", (double) 0.5);
        JSONObject gameConfigJson = new JSONObject();
        gameConfigJson.put("trading" , tradingJson);
        JSONObject mainJson = new JSONObject();
        mainJson.put("gameplayConfig" , gameConfigJson);
        gameConfigWithJustTrading = new GameConfig(mainJson);
        tradeConfig = new TradeConfig(gameConfigWithJustTrading);
    }
    public ITradeConfig getTradeConfig(){
        return tradeConfig;
    }


}
