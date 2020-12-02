package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class DraftPickTradeOffer extends TradeOfferAbstract {

    int roundToGive;
    PlayerDraftAbstract playerDraft;
    ITeam[][] playerDraftSequence = null;
    boolean tradePossible;
    private static final Logger logger = LogManager.getLogger(DraftPickTradeOffer.class);

    public DraftPickTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange, PlayerDraftAbstract playerDraft) {
        super(offeringTeam, receivingTeam, playersWantedInExchange);
        this.playerDraft = playerDraft;
        this.playerDraftSequence = playerDraft.getDraftPickSequence();
        this.tradePossible = false;
        logger.info("Draft trade between team: " + offeringTeam.getTeamName() + " and " + receivingTeam.getTeamName());
    }

    public void implementTrade() {
        this.roundToGive = setRoundFromDraft(offeringTeam);
        if (isTradePossible()) {
            logger.info("Implementing DraftTrade between team: " + offeringTeam.getTeamName() + " and " + receivingTeam.getTeamName());
            playerDraft.swapDraftPick(roundToGive, receivingTeam, offeringTeam);
            for (IPlayer player : playersWantedInExchange) {
                if (playerFound(player)) {
                    receivingTeam.getPlayers().remove(player);
                    offeringTeam.getPlayers().add(player);
                }
            }
        } else {
            logger.warn("Draft Trade not possible between team: " + offeringTeam.getTeamName() + " and " + receivingTeam.getTeamName());
        }
    }

    public int setRoundFromDraft(ITeam offeringTeam) {
        int round = -1;
        if (playerDraftSequence == null) {
            logger.warn("draft sequence not set");
        } else {
            for (int i = 0; i < playerDraftSequence.length; i++) {
                for (int j = 0; j < playerDraftSequence[i].length; j++) {
                    if (playerDraftSequence[i][j] == offeringTeam && j > round) {
                        round = j;
                        tradePossible = true;
                    }
                }
            }
            logger.info("Offering team will give round number: " + round + 1);
        }
        return round;
    }

    public boolean isTradePossible() {
        return tradePossible;
    }

    public boolean checkIfTradeAccepted() {
        setRoundFromDraft(offeringTeam);
        return tradePossible;
    }

    public boolean playerFound(IPlayer player) {
        if (player == null) {
            return false;
        } else {
            return true;
        }
    }
}
