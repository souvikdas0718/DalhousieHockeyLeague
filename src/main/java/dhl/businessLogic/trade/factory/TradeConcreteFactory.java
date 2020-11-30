package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.*;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.TradeOfferAbstract;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;

public class TradeConcreteFactory extends TradeAbstractFactory{

    public ITradeType createAiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig) {
        return new AiAiTrade(league, gameConfig);
    }

    public ITradeType createAiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league) {
        return new AiUserTrade(ioObject, league);
    }

    public TradeOfferAbstract createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType) {
        return new ExchangingPlayerTradeOffer(offeringTeam, receivingTeam, playersOffered, playersWantedInExchange, tradeType);
    }

    public IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam){
        IScout scout = new Scout(myTeam, myLeague, gameConfig, userTeam);
        return scout;
    }

    public TradeOfferAbstract createDraftPickTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange, IPlayerDraft playerDraft) {
        return new DraftPickTradeOffer(offeringTeam, receivingTeam, playersWantedInExchange,playerDraft);
    }

}
