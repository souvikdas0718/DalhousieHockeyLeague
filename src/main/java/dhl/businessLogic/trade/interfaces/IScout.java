package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.trade.TradeOfferAbstract;

public interface IScout {

    public TradeOfferAbstract findTrade(int congifMaxPlayerPerTrade);
}
