package dhl.trade.Interface;

import dhl.leagueModel.interfaceModel.ITeam;

public interface ITradeOffer {

    public void performTrade();

    public ITeam getOfferingTeam();

    public ITeam getReceivingTeam();

    public boolean isTradeGoodForReceivingTeam();
}
