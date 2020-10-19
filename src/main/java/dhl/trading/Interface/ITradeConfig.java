package dhl.trading.Interface;

import java.util.HashMap;

public interface ITradeConfig {

    public HashMap getTradingConfig();
    public void setTradingConfig(HashMap tradingConfig);

    public double getRandomAcceptanceChance();
    public void setRandomAcceptanceChance(double randomAcceptanceChance);

    public long getLossPoint();
    public void setLossPoint(long lossPoint);

    public long getMaxPlayersPerTrade();
    public void setMaxPlayersPerTrade(long maxPlayersPerTrade);

    public double getRandomTradeOfferChance();
    public void setRandomTradeOfferChance(double randomTradeOfferChance);
}
