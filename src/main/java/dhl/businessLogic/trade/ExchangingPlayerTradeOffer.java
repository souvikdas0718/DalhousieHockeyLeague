package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ExchangingPlayerTradeOffer implements ITradeOffer {

    ITeam offeringTeam;
    ITeam receivingTeam;
    public ArrayList<IPlayer> playersOffered, playersWantedInExchange;

    private static final Logger logger = LogManager.getLogger(ExchangingPlayerTradeOffer.class);

    public ExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange) {
        this.offeringTeam = offeringTeam;
        this.receivingTeam = receivingTeam;
        this.playersOffered = playersOffered;
        this.playersWantedInExchange = playersWantedInExchange;
        logger.info("Trade offer made between "+offeringTeam.getTeamName()+" and "+ receivingTeam.getTeamName());
    }

    public void implementTrade() {
        for (IPlayer player : playersOffered) {
            receivingTeam.getPlayers().add(player);
            offeringTeam.getPlayers().remove(player);
        }
        for (IPlayer player : playersWantedInExchange) {
            receivingTeam.getPlayers().remove(player);
            offeringTeam.getPlayers().add(player);
        }
    }

    public ITeam getOfferingTeam() {
        return offeringTeam;
    }

    public ITeam getReceivingTeam() {
        return receivingTeam;
    }

    public ArrayList<IPlayer> getOfferingPlayers() {
        return playersOffered;
    }

    public ArrayList<IPlayer> getPlayersWantedInReturn() {
        return playersWantedInExchange;
    }
}
