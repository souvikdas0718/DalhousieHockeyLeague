package dhl.trading;

import dhl.importJson.Interface.IGameConfig;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.trading.Interface.ITradeConfig;

public class ExecuteTrade {

    IGameConfig ourGameConfig;
    ITradeConfig tradeConfig;

    public ExecuteTrade(IGameConfig gameConfig){
        ourGameConfig = gameConfig;
        tradeConfig = new TradeConfig(ourGameConfig);
    }
}
