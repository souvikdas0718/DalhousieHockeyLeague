package dhl.trading.Interface;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public interface ITradeOffer {

    public boolean isTradeGoodForReceivingTeam();
    public double getPlayerCombinedStrength(ArrayList<IPlayer> players);
    public ITeam getOfferingTeam();
    public void setOfferingTeam(ITeam offeringTeam) ;
    public ITeam getReceivingTeam();
    public void setReceivingTeam(ITeam receivingTeam);
    public ArrayList<IPlayer> getPlayersOffered();
    public void setPlayersOffered(ArrayList<IPlayer> playersOffered);
    public ArrayList<IPlayer> getPlayersWantedInExchange();
    public void setPlayersWantedInExchange(ArrayList<IPlayer> playersWantedInExchange) ;

}
