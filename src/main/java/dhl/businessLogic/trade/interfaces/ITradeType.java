package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public interface ITradeType {

    public boolean isTradeAccepted(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted, ITeam receivingTeam);

    public void validateTeamRosterAfterTrade(ITeam team);
}

