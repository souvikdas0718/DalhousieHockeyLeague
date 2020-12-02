package dhl.businessLogic.trade.factory;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.TradeOfferAbstract;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class TradeAbstractFactory {

    private static TradeAbstractFactory uniqueInstance;
    private static final Logger logger = LogManager.getLogger(TradeAbstractFactory.class);

    protected TradeAbstractFactory() {

    }

    public static TradeAbstractFactory instance() {
        if (uniqueInstance == null) {
            logger.info("Singleton Object of TradeAbstractFactory made");
            uniqueInstance = new TradeConcreteFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(TradeAbstractFactory factory) {
        logger.info("Singleton Object of TradeAbstractFactory has been changed");
        uniqueInstance = factory;
    }

    public abstract ITradeType createAiAiTrade(ILeagueObjectModel league, IGameConfig gameConfig);

    public abstract ITradeType createAiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league);

    public abstract TradeOfferAbstract createExchangingPlayerTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playersWantedInExchange, ITradeType tradeType);

    public abstract IScout createScout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam);

    public abstract TradeOfferAbstract createDraftPickTradeOffer(ITeam offeringTeam, ITeam receivingTeam, ArrayList<IPlayer> playersWantedInExchange, PlayerDraftAbstract playerDraft);
}
