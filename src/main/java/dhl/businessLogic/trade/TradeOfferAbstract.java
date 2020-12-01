package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public abstract class TradeOfferAbstract {

    public ITeam offeringTeam;
    public ITeam receivingTeam;
    public ArrayList<IPlayer> playersWantedInExchange;

    public TradeOfferAbstract(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange) {
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

    public abstract boolean checkIfTradeAccepted();
}
