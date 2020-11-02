package dhl.trade.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface ITradeType {

    public boolean isTradeAccepted() throws Exception;

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception;

}

