package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.AiAiTrade;
import dhl.businessLogic.trade.AiUserTrade;
import dhl.businessLogic.trade.ExchangingPlayerTradeOffer;
import dhl.businessLogic.trade.Scout;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;

public class TradeConcreteFactory implements TradeAbstractFactory{

    public ITradeType createAiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig) {
        return new AiAiTrade(league, gameConfig);
    }

    public ITradeType createAiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league) {
        return new AiUserTrade(ioObject, league);
    }

    public ITradeOffer createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType) {
        return new ExchangingPlayerTradeOffer(offeringTeam, receivingTeam, playersOffered, playersWantedInExchange, tradeType);
    }

    public IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam){
        IScout scout = new Scout(myTeam, myLeague, gameConfig, userTeam);
        return scout;
    }

}
