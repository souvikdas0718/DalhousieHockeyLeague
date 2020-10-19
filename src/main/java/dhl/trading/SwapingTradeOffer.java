package dhl.trading;

import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trading.Interface.ITradeOffer;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

public class SwapingTradeOffer implements ITradeOffer {

    ITeam offeringTeam;
    ITeam receivingTeam;
    public ArrayList<IPlayer> playersOffered,playersWantedInExchange;

    public SwapingTradeOffer(ITeam offeringTeam,ITeam receivingTeam,ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange){

        this.offeringTeam = offeringTeam;
        this.receivingTeam = receivingTeam;
        this.playersOffered = playersOffered;
        this.playersWantedInExchange = playersWantedInExchange;
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
    public ITeam getOfferingTeam() {
        return offeringTeam;
    }

    public void setOfferingTeam(ITeam offeringTeam) {
        this.offeringTeam = offeringTeam;
    }

    public ITeam getReceivingTeam() {
        return receivingTeam;
    }

    public void setReceivingTeam(ITeam receivingTeam) {
        this.receivingTeam = receivingTeam;
    }

    public ArrayList<IPlayer> getPlayersOffered() {
        return playersOffered;
    }

    public void setPlayersOffered(ArrayList<IPlayer> playersOffered) {
        this.playersOffered = playersOffered;
    }

    public ArrayList<IPlayer> getPlayersWantedInExchange() {
        return playersWantedInExchange;
    }

    public void setPlayersWantedInExchange(ArrayList<IPlayer> playersWantedInExchange) {
        this.playersWantedInExchange = playersWantedInExchange;
    }

}
