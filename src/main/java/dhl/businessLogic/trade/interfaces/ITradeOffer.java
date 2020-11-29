package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public abstract class ITradeOffer {

    public ITeam offeringTeam;
    public ITeam receivingTeam;
    public ArrayList<IPlayer> playersWantedInExchange;

    public ITradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange){
        this.offeringTeam = offeringTeam;
        this.receivingTeam = receivingTeam;
        this.playersWantedInExchange = playersWantedInExchange;
    }
    public ITeam getOfferingTeam() {
        return offeringTeam;
    }

    public ITeam getReceivingTeam() {
        return receivingTeam;
    }

    public ArrayList<IPlayer> getPlayersWantedInReturn() {
        return playersWantedInExchange;
    }

    public abstract void implementTrade();
}
