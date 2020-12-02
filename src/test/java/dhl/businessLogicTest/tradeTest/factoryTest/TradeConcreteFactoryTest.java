package dhl.businessLogicTest.tradeTest.factoryTest;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TradeConcreteFactoryTest {

    TradeAbstractFactory testClassObject;

    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMocks;

    @BeforeEach
    public void initObject(){
        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMocks = LeagueModelMockAbstractFactory.instance();

        testClassObject = TradeAbstractFactory.instance();
    }

    @Test
    public void createAiAiTradeTest(){
        ILeagueObjectModel league = leagueMocks.createLeagueMock().getLeagueObjectModel();
        IGameConfig gameConfig = tradeMockFactory.createGameConfigMockForTrading().getGameConfigMock();
        Assertions.assertFalse(testClassObject.createAiAiTrade(league, gameConfig) == null);
    }

    @Test
    public void createAiUserTradeTest(){
        IUserInputOutput ioObject = IUserInputOutput.getInstance();
        ILeagueObjectModel league = leagueMocks.createLeagueMock().getLeagueObjectModel();
        Assertions.assertFalse(testClassObject.createAiUserTrade(ioObject, league) == null);
    }

    @Test
    public void createExchangingPlayerTradeOfferTest(){
        ITeam offeringTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        ITeam receivingTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        playersOffered.add(offeringTeam.getPlayers().get(0));
        ArrayList<IPlayer> playersWantedInExchange = new ArrayList<>();
        playersWantedInExchange.add(receivingTeam.getPlayers().get(0));

        ILeagueObjectModel league = leagueMocks.createLeagueMock().getLeagueObjectModel();
        IGameConfig gameConfig = tradeMockFactory.createGameConfigMockForTrading().getGameConfigMock();
        ITradeType tradeType = testClassObject.createAiAiTrade(league, gameConfig);

        Assertions.assertFalse(testClassObject.createExchangingPlayerTradeOffer(offeringTeam, receivingTeam, playersOffered, playersWantedInExchange, tradeType) == null);
    }

    @Test
    public void createScoutTest(){
        ITeam myTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        ITeam userTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        ILeagueObjectModel league = leagueMocks.createLeagueMock().getLeagueObjectModel();
        IGameConfig gameConfig = tradeMockFactory.createGameConfigMockForTrading().getGameConfigMock();

        Assertions.assertFalse(testClassObject.createScout(myTeam, league, gameConfig, userTeam) == null);
    }

    @Test
    public void createDraftPickTradeOfferTest(){
        ITeam offeringTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        ITeam receivingTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        ArrayList<IPlayer> playersWantedInExchange = new ArrayList<>();
        playersWantedInExchange.add(receivingTeam.getPlayers().get(0));

        PlayerDraftAbstract playerDraft = PlayerDraftAbstract.instance();
        Assertions.assertFalse(testClassObject.createDraftPickTradeOffer(offeringTeam, receivingTeam, playersWantedInExchange,playerDraft) == null);
    }
}
