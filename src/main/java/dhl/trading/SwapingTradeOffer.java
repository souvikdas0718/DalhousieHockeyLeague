package dhl.trading;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trading.Interface.ITradeOffer;

import java.util.ArrayList;

public class SwapingTradeOffer implements ITradeOffer {

    ITeam offeringTeam;
    ITeam receivingTeam;
    public ArrayList<IPlayer> playersOffered,playersWantedInExchange;

    public SwapingTradeOffer(ITeam offeringTeam,ITeam receivingTeam,ArrayList<IPlayer> playersOffered,
                             ArrayList<IPlayer> playersWantedInExchange){

        this.offeringTeam = offeringTeam;
        this.receivingTeam = receivingTeam;
        this.playersOffered = playersOffered;
        this.playersWantedInExchange = playersWantedInExchange;
    }

    @Override
    public boolean isTradeGoodForReceivingTeam(){

        ITeam tempObjectofRecevingTeam = this.receivingTeam;
        ITeam tempObjectofOfferingTeam = this.offeringTeam;

        // TODO: 18-10-2020 uncomment when method implemented
        //Double currentStrength = tempObjectofOfferingTeam.calculateTeamStrength();

        ArrayList<IPlayer> offeringTeamPlayers = tempObjectofOfferingTeam.getPlayers();
        ArrayList<IPlayer> recevingTeamPlayers = tempObjectofRecevingTeam.getPlayers();

        switchPlayers(recevingTeamPlayers,offeringTeamPlayers,playersWantedInExchange);
        switchPlayers(offeringTeamPlayers,recevingTeamPlayers,playersOffered);

        tempObjectofOfferingTeam.setPlayers(offeringTeamPlayers);
        tempObjectofRecevingTeam.setPlayers(recevingTeamPlayers);
        // TODO: 18-10-2020  uncomment when method implemented
        /*
        if(tempObjectofRecevingTeam.calculateTeamStrength() > currentStrength ){
            return true;
        }*/
        return false;
    }

    private void switchPlayers(ArrayList<IPlayer> removeFromList, ArrayList<IPlayer> addToList, ArrayList<IPlayer> players) {
        for(int i=0;i< players.size();i++){
            removeFromList.remove(players.get(i));
            addToList.add(players.get(i));
        }
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
