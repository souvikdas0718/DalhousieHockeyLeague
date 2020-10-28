package dhl.trading.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public interface IGenrateTradeOffer {

    public void makeTrade(ITeam tradingTeam , ILeagueObjectModel leagueObjectModel) throws Exception;

    public ArrayList<IPlayer> sortPlayerList(ITeam tradingTeam) throws Exception;

    public ITradeOffer makeSwapTradeOffer(ITeam teamOffering, ITeam  teamGettingOffer) throws Exception;
}
