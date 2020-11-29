package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.Mocks.MockUserInputOutput;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
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
        testClassObject = (TradingEngine) ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
        testClassObject.setIoObject(ioObject);
    }

    @Test
    public void startEngine() {
        badTeamMock.setRoster();
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        testClassObject.startEngine();
        //Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }


    @Test
    public void performTradeTest(){
        testClassObject.performTrade(badTeamMock);
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    /*
    @Test
    public void sendTradeToRecevingTeamTest() {
        ILeagueObjectModel leagueObjectModel = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        IScout teamScout = tradeFactory.createScout(badTeamMock, leagueObjectModel, ourGameConfig);
        int congifMaxPlayerPerTrade = Integer.parseInt(ourGameConfig.getValueFromOurObject(ourGameConfig.getTrading(), ourGameConfig.getMaxPlayersPerTrade()));

        ITradeOffer tradeOffer = teamScout.findTrade(congifMaxPlayerPerTrade);

        badTeamMock.setRoster();
        double teamStrength = badTeamMock.calculateTeamStrength();

        ((MockUserInputOutput) ioObject).setMockOutput("2");
        testClassObject.sendTradeToRecevingTeam(tradeOffer, tradeOffer.getReceivingTeam());
        double noChangeInStrength = badTeamMock.calculateTeamStrength();
        Assertions.assertTrue(teamStrength == noChangeInStrength);

        ((MockUserInputOutput) ioObject).setMockOutput("1");
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.sendTradeToRecevingTeam(tradeOffer, tradeOffer.getReceivingTeam());
        });
        badTeamMock.setRoster();
        double increasedTeamStrength = badTeamMock.calculateTeamStrength();
        Assertions.assertTrue(teamStrength < increasedTeamStrength );
    }*/

    @Test
    public void getCurrentTradeTest(){
        ITradingEngine.setFactory(new TradingEngine(ourGameConfig, leagueMock, userTeam));
        testClassObject = (TradingEngine) ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        testClassObject.performTrade(badTeamMock);
        ITradeOffer tradeOffer = testClassObject.getCurrentTrade();
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
    }

    @Test
    public void findLossPointOfTheTeamTest() {
        tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer().setLossPoint(10);
        int losspoint = testClassObject.findLossPointOfTheTeam(tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer());
        Assertions.assertTrue(losspoint == 10);
    }

}
