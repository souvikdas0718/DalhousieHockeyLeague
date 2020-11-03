package dhl.businessLogic.trade.Interface;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface ITradeType {

    public boolean isTradeAccepted() throws Exception;

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception;

}

