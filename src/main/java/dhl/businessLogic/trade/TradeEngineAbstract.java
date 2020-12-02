package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public abstract class TradeEngineAbstract {
    private static TradeEngineAbstract uniqueInstance;

    public static TradeEngineAbstract instance(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel, ITeam userTeam) {
        if (uniqueInstance == null) {
            uniqueInstance = new TradingEngine(gameConfig, leagueObjectModel, userTeam);
        }
        return uniqueInstance;
    }

    public static void setFactory(TradeEngineAbstract engine) {
        uniqueInstance = engine;
    }

    public abstract void startEngine();

    public abstract void performTrade(ITeam tradingTeam);
}
