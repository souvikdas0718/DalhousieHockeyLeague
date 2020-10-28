package dhl.tradingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.importJson.GameConfig;
import dhl.importJson.Interface.IGameConfig;
import dhl.trading.TradingController;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradingControllerTest {

    TradingController testClassObject;
    LeagueObjectModelMocks mocks;

    @BeforeEach
    public void initObject(){
        JSONObject tradingJson = new JSONObject();
        tradingJson.put("lossPoint", 3);
        tradingJson.put("randomTradeOfferChance",0.5);
        tradingJson.put("maxPlayersPerTrade",2);
        tradingJson.put("randomAcceptanceChance",0.5);
        JSONObject gameConfigJson = new JSONObject();
        gameConfigJson.put("trading" , tradingJson);
        JSONObject mainJson = new JSONObject();
        mainJson.put("gameplayConfig" , gameConfigJson);
        IGameConfig gameConfig = new GameConfig(mainJson);
        //mocks = new LeagueObjectModelMocks();
        //testClassObject = new TradingController(mocks.getLeagueObjectMock() , gameConfig);
    }

    @Test
    public void startTradingTest(){

    }
}
