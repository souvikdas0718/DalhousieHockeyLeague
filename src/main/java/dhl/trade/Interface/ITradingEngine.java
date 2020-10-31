package dhl.trade.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface ITradingEngine {

    public void makeOffer(ITeam tradingTeam) throws Exception;

    public void sendTradeToRecevingTeam(ITeam userTeam);

    public void checkPlayersAfterTrade();

    public ITradeOffer getCurrentTrade();
}
