package dhl.trading;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.trading.Interface.ITradeConfig;

public class ExecuteTrade {

    IGameConfig ourGameConfig;
    ITradeConfig tradeConfig;

    public ExecuteTrade(IGameConfig gameConfig){
        ourGameConfig = gameConfig;
        tradeConfig = new TradeConfig(ourGameConfig);
    }
}
