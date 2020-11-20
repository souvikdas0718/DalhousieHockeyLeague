package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.TradingEngine;
import dhl.inputOutput.importJson.interfaces.IGameConfig;

public abstract class ITradingEngine {
    private static ITradingEngine uniqueInstance;

    public static ITradingEngine instance(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel, ITeam userTeam) {
        if (uniqueInstance == null) {
            uniqueInstance = new TradingEngine(gameConfig, leagueObjectModel, userTeam);
        }
        return uniqueInstance;
    }

    public static void setFactory(ITradingEngine engine) {
        uniqueInstance = engine;
    }

    public abstract void startEngine();
}
