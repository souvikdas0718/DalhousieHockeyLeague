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
    public void implementTrade() {
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
    public ITeam getOfferingTeam() {
        return offeringTeam;
    }

    @Override
    public ITeam getReceivingTeam() {
        return receivingTeam;
    }

    public ArrayList<IPlayer> getOfferingPlayers(){
        return playersOffered;
    }

    public ArrayList<IPlayer> getPlayersWantedInReturn(){
        return playersWantedInExchange;
    }
}
