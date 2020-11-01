package dhl.trade;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.Interface.ITradeType;

import java.util.ArrayList;

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
        if(isTradeGoodForReceivingTeam(tradeOffer)){
            return true;
        }else if(randomValue > configRandomAcceptanceChance){
            return true;
        }
        return false;
    }

    public boolean isTradeGoodForReceivingTeam(ITradeOffer tradeOffer){
        if(getPlayerCombinedStrength(tradeOffer.getOfferingPlayers()) > getPlayerCombinedStrength(tradeOffer.getPlayersWantedInReturn())){
            return true;
        }
        return false;
    }

    public double getPlayerCombinedStrength(ArrayList<IPlayer> players){
        double totalstrength = 0;
        for(int i=0;i<players.size();i++){
            totalstrength+= players.get(i).getPlayerStrength();
        }
        return totalstrength;
    }

}
