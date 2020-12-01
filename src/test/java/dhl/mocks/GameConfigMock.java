package dhl.mocks;

import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import org.json.simple.JSONObject;

public class GameConfigMock {

    IGameConfig gameConfig;
    public double randomAcceptanceChance, randomTradeOfferChance;
    public long lossPoint, maxPlayersPerTrade;
    public GameConfigMock(){
        lossPoint = 6;
        maxPlayersPerTrade = 5;
        this.randomAcceptanceChance = 0.0;
        this.randomTradeOfferChance = 0.0;
        initObject();
    }
    public void initObject(){
        JSONObject gameConfigJson = new JSONObject();
        gameConfigJson.put("trading", getTradingJsonObject());
        JSONObject mainJson = new JSONObject();
        mainJson.put("gameplayConfig", gameConfigJson);
        gameConfig = new GameConfig(mainJson);
    }

    public JSONObject getTradingJsonObject() {
        JSONObject tradingJson = new JSONObject();
        tradingJson.put("lossPoint", lossPoint);
        tradingJson.put("randomTradeOfferChance", randomTradeOfferChance);
        tradingJson.put("maxPlayersPerTrade", maxPlayersPerTrade);
        tradingJson.put("randomAcceptanceChance", randomAcceptanceChance);
        JSONObject gmTable = new JSONObject();
        gmTable.put("shrewd",-0.1);
        gmTable.put("gambler",0.1);
        gmTable.put("normal",0.0);
        tradingJson.put("gmTable", gmTable);
        return tradingJson;
    }

    public IGameConfig getGameConfigMock() {
        return gameConfig;
    }

    public void setRandomAcceptanceChance(double randomAcceptanceChance) {
        this.randomAcceptanceChance = randomAcceptanceChance;
        initObject();
    }

    public void setRandomTradeOfferChance(double randomTradeOfferChance) {
        this.randomTradeOfferChance = randomTradeOfferChance;
        initObject();
    }

    public void setLossPoint(long lossPoint) {
        this.lossPoint = lossPoint;
        initObject();
    }

    public void setMaxPlayersPerTrade(long maxPlayersPerTrade) {
        this.maxPlayersPerTrade = maxPlayersPerTrade;
        initObject();
    }
}
