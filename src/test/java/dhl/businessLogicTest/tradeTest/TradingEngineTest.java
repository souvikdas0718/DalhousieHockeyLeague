package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.TradeEngineAbstract;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import dhl.mocks.MockUserInputOutput;
import dhl.businessLogic.trade.TradeOfferAbstract;
import dhl.businessLogic.trade.TradingEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TradingEngineTest {

    TradingEngine testClassObject;
    ITeam goodTeamMock, badTeamMock;
    ILeagueObjectModel leagueMock;
    IUserInputOutput ioObject;
    ITeam userTeam;
    IGameConfig ourGameConfig;

    GameConfigMockForTrading gameConfigMock;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach
    public void initObject() {
        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

        ioObject = new MockUserInputOutput();

        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        ourGameConfig = gameConfigMock.getGameConfigMock();

        goodTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        leagueMock = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);

        IGeneralManager manager = leagueFactory.createGeneralManager("Manager" , "normal");
        ICoach coach = leagueFactory.createCoach("coach", 10,10,10,10);
        List<IPlayer> playersList = leagueMock.getFreeAgents();
        userTeam = leagueFactory.createTeam("ABC" , manager, coach, playersList );
        leagueMock.setFreeAgents(tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents());
        testClassObject = (TradingEngine) TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
        testClassObject.setIoObject(ioObject);
    }

    @Test
    public void startEngineTest() {
        ourGameConfig = gameConfigMock.getGameConfigMock();
        TradeEngineAbstract.setFactory(new TradingEngine(ourGameConfig, leagueMock, userTeam));
        testClassObject = (TradingEngine) TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        testClassObject.startEngine();
        badTeamMock.setRoster();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }

    @Test
    public void startEngineTestWithRejectionRate(){
        gameConfigMock.setRandomAcceptanceChance(1.0);
        ourGameConfig = gameConfigMock.getGameConfigMock();
        badTeamMock.setLossPoint(10);
        TradeEngineAbstract.setFactory(new TradingEngine(ourGameConfig, leagueMock, userTeam));
        testClassObject = (TradingEngine) TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        testClassObject.startEngine();
        badTeamMock.setRoster();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }

    @Test
    public void performTradeTest(){
        testClassObject.performTrade(badTeamMock);
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    @Test
    public void getCurrentTradeTest(){
        TradeEngineAbstract.setFactory(new TradingEngine(ourGameConfig, leagueMock, userTeam));
        testClassObject = (TradingEngine) TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        testClassObject.performTrade(badTeamMock);
        TradeOfferAbstract tradeOffer = testClassObject.getCurrentTrade();
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
    }

    @Test
    public void findLossPointOfTheTeamTest() {
        tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer().setLossPoint(10);
        int losspoint = testClassObject.findLossPointOfTheTeam(tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer());
        Assertions.assertTrue(losspoint == 10);
    }

}
