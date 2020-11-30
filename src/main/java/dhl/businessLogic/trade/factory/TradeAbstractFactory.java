package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import java.util.ArrayList;

public abstract class TradeAbstractFactory {

    private static TradeAbstractFactory uniqueInstance;

    protected TradeAbstractFactory(){

    }

    public static TradeAbstractFactory instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TradeConcreteFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(TradeAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract ITradeType createAiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig);

    public abstract ITradeType createAiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league);

    public abstract ITradeOffer createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType);

    public abstract IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam);

    public abstract ITradeOffer createDraftPickTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange, IPlayerDraft playerDraft);
}
