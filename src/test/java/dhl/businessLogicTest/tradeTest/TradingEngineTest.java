package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.*;
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
import java.util.ArrayList;
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
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        badTeamMock.setRoster();
        testClassObject.startEngine();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }

    @Test
    public void performTradeTest() throws Exception {
        // TODO: 25-11-2020 update Test
//        testClassObject.performTrade(badTeamMock);
//        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    @Test
    public void sendTradeToRecevingTeamTest() throws Exception {
        ITradeOffer tradeOffer = testClassObject.generateTradeOffer(badTeamMock, goodTeamMock);
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
    }

    @Test
    public void getCurrentTradeTest() throws Exception {
        testClassObject = new TradingEngine(ourGameConfig, leagueMock, userTeam);
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        testClassObject.performTrade(badTeamMock);
        ITradeOffer tradeOffer = testClassObject.getCurrentTrade();
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
    }

    @Test
    public void findTeamToTradeWithTest() throws Exception {

        // TODO: 23-11-2020 check
        testClassObject = new TradingEngine(ourGameConfig, leagueMock, userTeam);
        //testClassObject = (TradingEngine) ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        leagueMock = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        ITeam team = testClassObject.findTeamToTradeWith(badTeamMock);

        Assertions.assertTrue(team.getTeamName().length() > 0);
    }

    @Test
    public void findLossPointOfTheTeamTest() {
        tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer().setLossPoint(10);
        int losspoint = testClassObject.findLossPointOfTheTeam(tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer());
        Assertions.assertTrue(losspoint == 10);
    }

    @Test
    public void generateTradeOfferTest() throws Exception {
        ITradeOffer tradeOffer = testClassObject.generateTradeOffer(badTeamMock, goodTeamMock);
        Assertions.assertFalse(tradeOffer == null);
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
        Assertions.assertTrue(tradeOffer.getReceivingTeam() == goodTeamMock);
    }

    @Test
    public void isPlayerNotInWantedListTest() {
        IPlayer player = tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("player1", PlayerPosition.DEFENSE.toString());
        ArrayList<IPlayer> playerList = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        Assertions.assertTrue(testClassObject.isPlayerNotInWantedList(player, playerList));
        playerList.add(player);
        Assertions.assertFalse(testClassObject.isPlayerNotInWantedList(player, playerList));
    }

    @Test
    public void sortPlayerListTest() throws Exception {
        ITeam unsortedTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        IPlayerStatistics statistics = leagueFactory.createPlayerStatistics( 5, 3, 2, 1);
        statistics.setAge(25);
        IPlayer player = leagueFactory.createPlayer("PlayerA", "defense", false,statistics);

        unsortedTeam.getPlayers().add(player);

        statistics = leagueFactory.createPlayerStatistics( 7, 3, 9, 7);
        statistics.setAge(34);
        player = leagueFactory.createPlayer("AnotherPlayer", "defense", false,statistics);
        unsortedTeam.getPlayers().add(player);

        testClassObject.sortPlayerList(unsortedTeam);
        Assertions.assertTrue(unsortedTeam.getPlayers().get(0).getPlayerStrength() <= unsortedTeam.getPlayers().get(1).getPlayerStrength());

        IGeneralManager manager = leagueFactory.createGeneralManager("Larry", "normal");
        Team empytyPlayerTeam = (Team) leagueFactory.createTeam("EmptyPlayers", manager, new Coach(), new ArrayList<>());

        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.sortPlayerList(empytyPlayerTeam);
        });
        Assertions.assertTrue(error.getMessage().contains("EmptyPlayers Team have no players"));
    }

    @Test
    public void isTeamDifferentTest() {
        IGeneralManager manager = new GeneralManager("Larry", "normal");
        Team newTeam = new Team("team1", manager, new Coach(), new ArrayList<>());
        Assertions.assertFalse(testClassObject.isTeamDifferent(newTeam, newTeam));
        Team otherTeam = new Team("secondTeam", manager, new Coach(), new ArrayList<>());
        Assertions.assertTrue(testClassObject.isTeamDifferent(newTeam, otherTeam));
    }

    @Test
    public void isTeamGoodForTradingTest() throws Exception {
        Assertions.assertTrue(testClassObject.isTeamGoodForTrading(badTeamMock, goodTeamMock));
        Assertions.assertFalse(testClassObject.isTeamGoodForTrading(goodTeamMock, badTeamMock));
    }

}
