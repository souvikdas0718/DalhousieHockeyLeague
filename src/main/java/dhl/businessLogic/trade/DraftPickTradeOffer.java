package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerDraft;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.interfaces.ITradeOffer;

import java.util.ArrayList;

public class DraftPickTradeOffer extends ITradeOffer {

    int roundToGive;
    IPlayerDraft playerDraft;
    ITeam[][] playerDraftSequence;
    boolean tradePossible;

    public DraftPickTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange, IPlayerDraft playerDraft) {
        super(offeringTeam, receivingTeam, playersWantedInExchange);
        this.playerDraft = playerDraft;
        this.playerDraftSequence = playerDraft.getDraftPickSequence();
        this.tradePossible = false;
        this.roundToGive = setRoundFromDraft(offeringTeam);
    }

    public void implementTrade() {
        if (isTradePossible()){
            playerDraft.swapDraftPick(roundToGive, receivingTeam, offeringTeam);
            for (IPlayer player : playersWantedInExchange) {
                receivingTeam.getPlayers().remove(player);
                offeringTeam.getPlayers().add(player);
            }
        }
    }

    public int setRoundFromDraft(ITeam offeringTeam){
        int round = -1;
        for(int i = 0; i < playerDraftSequence.length; i++){
            for (int j = 0; j < playerDraftSequence[i].length; j++){
                if (playerDraftSequence[i][j] == offeringTeam && j > round){
                    round = j;
                    tradePossible = true;
                }
            }
        }
        return round;
    }

    public int getRoundToGive() {
        return roundToGive;
    }

    public boolean isTradePossible() {
        return tradePossible;
    }
}
