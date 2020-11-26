package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.IUserInputOutput;

import java.util.ArrayList;

public interface TradeAbstractFactory {

    public ITradeType createAiAiTrade(ITradeOffer tradeOffer, IGameConfig gameConfig);

    public ITradeType createAiUserTrade(ITradeOffer tradeOffer, IUserInputOutput ioObject, ITeamRosterUpdater updateUserTeamRoster);

    public ITradeOffer createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange);

    public IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig);
}
