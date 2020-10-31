package dhl.trade;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.Interface.ITradeType;

public class AiAiTrade implements ITradeType {

    ITradeOffer tradeOffer;
    IGameConfig gameConfig;
    TradeConfigVariableNames tradeConfigVariableNames;
    public AiAiTrade(ITradeOffer tradeOffer, IGameConfig gameConfig){
        this.tradeOffer = tradeOffer;
        this.gameConfig = gameConfig;
        tradeConfigVariableNames = new TradeConfigVariableNames();
    }

    @Override
    public boolean isTradeAccepted() {
        // TODO: 30-10-2020 random value fix
        double configRandomAcceptanceChance = Double.parseDouble(gameConfig.getValueFromOurObject(tradeConfigVariableNames.getRandomAcceptanceChance()));
        double randomValue = Math.random();
        if(tradeOffer.isTradeGoodForReceivingTeam()){

            return true;
        }else if(randomValue > configRandomAcceptanceChance){
            return true;
        }
        return false;
    }

}
