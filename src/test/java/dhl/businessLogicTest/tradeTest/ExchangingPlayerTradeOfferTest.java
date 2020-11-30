package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.ExchangingPlayerTradeOffer;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ExchangingPlayerTradeOfferTest {

    ExchangingPlayerTradeOffer testClassObject;
    IGameConfig ourGameConfig;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMock;

    @BeforeEach
    public void initObject() {
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();

        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMock = LeagueModelMockAbstractFactory.instance();

        ITeam strongTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        ITeam weakTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        playersOffered.add(weakTeam.getPlayers().get(0));
        playersWanted.add(strongTeam.getPlayers().get(0));


        ILeagueObjectModel league = leagueMock.createLeagueMock().getLeagueObjectModel();
        ourGameConfig = tradeMockFactory.createGameConfigMockForTrading().getGameConfigMock();
        ITradeType tradeType = tradeType = tradeFactory.createAiAiTrade(league, ourGameConfig);
        testClassObject = (ExchangingPlayerTradeOffer) tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted, tradeType);
    }

    @Test
    public void performTradeTest() {

        testClassObject.implementTrade();
        IPlayer playerNowInStrongTeam = testClassObject.playersOffered.get(0);
        IPlayer playerNowInWeakTeam = testClassObject.playersWantedInExchange.get(0);

        Assertions.assertTrue(testClassObject.getOfferingTeam().getPlayers().contains(playerNowInWeakTeam));
        Assertions.assertTrue(testClassObject.getReceivingTeam().getPlayers().contains(playerNowInStrongTeam));
    }

    @Test
    public void getOfferingTeamTest() {
        Assertions.assertTrue(testClassObject.getOfferingTeam().getTeamName() == "TeamWithBadPlayer");
    }

    @Test
    public void getReceivingTeamTest() {
        Assertions.assertTrue(testClassObject.getReceivingTeam().getTeamName() == "TeamWithGoodPlayer");
    }

    @Test
    public void getOfferingPlayersTest() {
        ArrayList<IPlayer> offeredPlayers = testClassObject.getOfferingPlayers();
        String offeredPlayerName = offeredPlayers.get(0).getPlayerName();
        Assertions.assertTrue(offeredPlayerName.equals("WeakPlayer1"));
    }

    @Test
    public void getPlayersWantedInReturn() {
        ArrayList<IPlayer> playersWanted = testClassObject.getPlayersWantedInReturn();
        String wantedPlayerName = playersWanted.get(0).getPlayerName();
        Assertions.assertTrue(wantedPlayerName.equals("Player1"));
    }
}
