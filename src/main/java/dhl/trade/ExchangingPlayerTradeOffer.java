package dhl.trade;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.Interface.ITradeOffer;

import java.util.ArrayList;

public class ExchangingPlayerTradeOffer implements ITradeOffer {

    ITeam offeringTeam;
    ITeam receivingTeam;
    public ArrayList<IPlayer> playersOffered,playersWantedInExchange;

    public ExchangingPlayerTradeOffer(ITeam offeringTeam , ITeam receivingTeam , ArrayList<IPlayer> playersOffered , ArrayList<IPlayer> playersWantedInExchange){
        this.offeringTeam = offeringTeam;
        this.receivingTeam = receivingTeam;
        this.playersOffered = playersOffered;
        this.playersWantedInExchange = playersWantedInExchange;
    }


    @Override
    public void performTrade() {
        for(IPlayer player: playersOffered){
            receivingTeam.getPlayers().add(player);
            offeringTeam.getPlayers().remove(player);
        }
        for(IPlayer player: playersWantedInExchange){
            receivingTeam.getPlayers().remove(player);
            offeringTeam.getPlayers().add(player);
        }
    }

    @Override
    public boolean isTradeGoodForReceivingTeam(){
        if(getPlayerCombinedStrength(playersOffered) > getPlayerCombinedStrength(playersWantedInExchange)){
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

    @Override
    public ITeam getOfferingTeam() {
        return offeringTeam;
    }

    @Override
    public ITeam getReceivingTeam() {
        return receivingTeam;
    }
}
