package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.interfaces.ITradeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ExchangingPlayerTradeOffer extends TradeOfferAbstract {

    public ArrayList<IPlayer> playersOffered;
    protected ITradeType currentTradeType;

    private static final Logger logger = LogManager.getLogger(ExchangingPlayerTradeOffer.class);

    public ExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType) {
        super(offeringTeam, receivingTeam, playersWantedInExchange);
        this.currentTradeType = tradeType;
        this.playersOffered = playersOffered;
        logger.info("Player Swap Trade offer made between " + offeringTeam.getTeamName() + " and " + receivingTeam.getTeamName());
    }

    public void implementTrade() {
        if (checkIfTradeAccepted()) {
            logger.info("Implementing trade between " + offeringTeam.getTeamName() + " and " + receivingTeam.getTeamName());
            for (IPlayer player : playersOffered) {
                if (playerFound(player)) {
                    receivingTeam.getPlayers().add(player);
                    offeringTeam.getPlayers().remove(player);
                }
            }
            for (IPlayer player : playersWantedInExchange) {
                if (playerFound(player)) {
                    receivingTeam.getPlayers().remove(player);
                    offeringTeam.getPlayers().add(player);
                }
            }
            currentTradeType.validateTeamRosterAfterTrade(offeringTeam);
            currentTradeType.validateTeamRosterAfterTrade(receivingTeam);
        }
    }

    public ArrayList<IPlayer> getOfferingPlayers() {
        return playersOffered;
    }

    public boolean checkIfTradeAccepted() {
        return currentTradeType.isTradeAccepted(playersOffered, playersWantedInExchange, receivingTeam);
    }

    public boolean playerFound(IPlayer player) {
        if (player == null) {
            return false;
        } else {
            return true;
        }
    }
}
