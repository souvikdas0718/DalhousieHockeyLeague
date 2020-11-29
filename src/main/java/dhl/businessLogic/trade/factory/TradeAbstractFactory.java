package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;

public interface TradeAbstractFactory {

    public ITradeType createAiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig);

    public ITradeType createAiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league);

    public ITradeOffer createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType);

    public IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam);
}
