package dhl.trade.Interface;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public interface ITradeOffer {

    public void implementTrade();

    public ITeam getOfferingTeam();

    public ITeam getReceivingTeam();

    public ArrayList<IPlayer> getOfferingPlayers();

    public ArrayList<IPlayer> getPlayersWantedInReturn();
}
