package dhl.trading;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.trading.Interface.ITradeConfig;

import java.util.HashMap;

public class TradeConfig implements ITradeConfig {

    HashMap tradingConfig;

    long lossPoint,maxPlayersPerTrade;
    double randomAcceptanceChance,randomTradeOfferChance;

    public TradeConfig(IGameConfig gameConfig){

        tradingConfig = gameConfig.getHashMap("trading");
        updateConfig();

    }

    public void updateConfig(){
        randomAcceptanceChance = (double) tradingConfig.get("randomAcceptanceChance");
        randomTradeOfferChance = (double) tradingConfig.get("randomTradeOfferChance");
        lossPoint = (long) tradingConfig.get("lossPoint");
        maxPlayersPerTrade = (long) tradingConfig.get("maxPlayersPerTrade");
    }

    @Override
    public HashMap getTradingConfig(){
        return tradingConfig;
    }

    @Override
    public void setTradingConfig(HashMap tradingConfig) {

        this.tradingConfig = tradingConfig;
        updateConfig();
    }

    @Override
    public double getRandomAcceptanceChance() {
        return randomAcceptanceChance;
    }

    @Override
    public void setRandomAcceptanceChance(double randomAcceptanceChance) {
        this.randomAcceptanceChance = randomAcceptanceChance;
    }

    @Override
    public long getLossPoint() {
        return lossPoint;
    }

    @Override
    public void setLossPoint(long lossPoint) {
        this.lossPoint = lossPoint;
    }

    @Override
    public long getMaxPlayersPerTrade() {
        return maxPlayersPerTrade;
    }

    @Override
    public void setMaxPlayersPerTrade(long maxPlayersPerTrade) {
        this.maxPlayersPerTrade = maxPlayersPerTrade;
    }

    @Override
    public double getRandomTradeOfferChance() {
        return randomTradeOfferChance;
    }

    @Override
    public void setRandomTradeOfferChance(double randomTradeOfferChance) {
        this.randomTradeOfferChance = randomTradeOfferChance;
    }
}
