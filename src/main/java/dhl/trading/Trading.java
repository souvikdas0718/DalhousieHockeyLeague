package dhl.trading;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.trading.Interface.ITradeConfig;

public class Trading {

    IGameConfig ourGameConfig;
    ITradeConfig tradeConfig;


    public Trading(IGameConfig gameConfig){
        ourGameConfig = gameConfig;
        tradeConfig = new TradeConfig(ourGameConfig);
    }

    public void startTrading(){
        // TODO: 26-10-2020 call method to calculat

    }

    public void findLossPointOfTheTeam(ITeam team){

        // TODO: 27-10-2020 Talk to team about getting loss points
    }


}
