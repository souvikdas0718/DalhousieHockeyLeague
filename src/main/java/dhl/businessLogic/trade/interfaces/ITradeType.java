package dhl.businessLogic.trade.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface ITradeType {
    public boolean isTradeAccepted();
    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel);
}

