package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public interface ITradeOffer {

    public void implementTrade();

    public ITeam getOfferingTeam();

    public ITeam getReceivingTeam();

    public ArrayList<IPlayer> getOfferingPlayers();

    public ArrayList<IPlayer> getPlayersWantedInReturn();
}
