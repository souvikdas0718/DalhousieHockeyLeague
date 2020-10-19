package dhl.trading;

import dhl.trading.Interface.ITradeAcceptReject;
import dhl.trading.Interface.ITradeConfig;
import dhl.trading.Interface.ITradeOffer;

public class TradeAcceptReject implements ITradeAcceptReject {

    ITradeOffer currentTrade;
    ITradeConfig ourTradeConfig;
    public TradeAcceptReject(ITradeOffer tradeOffer, ITradeConfig tradeConfig){
        currentTrade = tradeOffer;
        ourTradeConfig = tradeConfig;
    }

    @Override
    public boolean isTradeAccepted(){
        if(currentTrade.isTradeGoodForReceivingTeam()){
            return true;
        }else if(Math.random() > ourTradeConfig.getRandomAcceptanceChance()){
            return true;
        }
        return false;
    }
}