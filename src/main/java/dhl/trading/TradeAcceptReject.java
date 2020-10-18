package dhl.trading;

import dhl.trading.Interface.ITradeAcceptReject;
import dhl.trading.Interface.ITradeOffer;

public class TradeAcceptReject implements ITradeAcceptReject {

    ITradeOffer currentTrade;

    public TradeAcceptReject(ITradeOffer tradeOffer){
        currentTrade = tradeOffer;
    }

    @Override
    public boolean isTradeAccepted(){
        if(currentTrade.isTradeGoodForReceivingTeam()){
            return true;
        }else if(Math.random() > 0.5){
            // TODO: 18-10-2020 change 0.5 to gameconfig variable;
            return true;
        }
        return false;
    }

}
