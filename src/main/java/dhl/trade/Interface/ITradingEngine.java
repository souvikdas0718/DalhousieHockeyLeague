package dhl.trade.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface ITradingEngine {

    public void performTrade(ITeam tradingTeam) throws Exception;

    public ITradeOffer getCurrentTrade();
}
