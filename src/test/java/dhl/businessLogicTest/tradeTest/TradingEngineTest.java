package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.MockUserInputOutput;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.TradingEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TradingEngineTest {

    TradingEngine testClassObject;
    GameConfigMock gameConfigMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    TradeMock tradeMock;
    ITeam goodTeamMock, badTeamMock;
    ILeagueObjectModel leagueMock;
    IUserInputOutput ioObject;
    ITeam userTeam;
    IGameConfig ourGameConfig;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach
    public void initObject() {
        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();

        ioObject = new MockUserInputOutput();
        tradeMock = new TradeMock();
        gameConfigMock = new GameConfigMock();
        leagueObjectModelMocks = new LeagueObjectModelMocks();

        goodTeamMock = tradeMock.getTeamWithGoodPlayer();
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        leagueMock = leagueObjectModelMocks.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        ourGameConfig = gameConfigMock.getGameConfigMock();

        IGeneralManager manager = leagueFactory.createGeneralManager("Manager" , "normal");
        ICoach coach = leagueFactory.createCoach("coach", 10,10,10,10);
        List<IPlayer> playersList = leagueMock.getFreeAgents();
        userTeam = leagueFactory.createTeam("ABC" , manager, coach, playersList );
        leagueMock.setFreeAgents(tradeMock.get50FreeAgents());
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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        testClassObject.performTrade(goodTeamMock);

        //Assertions.assertTrue(outContent.toString().contains("No Good Player availabe to swap for Team:"));
        Assertions.assertTrue(goodTeamMock.getLossPoint() > 0);

        testClassObject.performTrade(badTeamMock);
        // TODO: 23-11-2020 test 
        //Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
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
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        testClassObject.performTrade(badTeamMock);
        ITradeOffer tradeOffer = testClassObject.getCurrentTrade();
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
    }

    @Test
    public void findTeamToTradeWithTest() throws Exception {
        //testClassObject = (TradingEngine) ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
        // TODO: 23-11-2020 check
        testClassObject = new TradingEngine(ourGameConfig, leagueMock, userTeam);
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        leagueMock = leagueObjectModelMocks.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        ITeam team = testClassObject.findTeamToTradeWith(badTeamMock);
        System.out.println(team.getTeamName());
        Boolean result = team.getTeamName().equals("TeamWithGoodPlayer");
        Assertions.assertTrue(result);
    }

    @Test
    public void findLossPointOfTheTeamTest() {
        tradeMock.getTeamWithBadPlayer().setLossPoint(10);
        int losspoint = testClassObject.findLossPointOfTheTeam(tradeMock.getTeamWithBadPlayer());
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
        IPlayer player = tradeMock.getStrongPlayer("player1");
        ArrayList<IPlayer> playerList = tradeMock.get50FreeAgents();
        Assertions.assertTrue(testClassObject.isPlayerNotInWantedList(player, playerList));
        playerList.add(player);
        Assertions.assertFalse(testClassObject.isPlayerNotInWantedList(player, playerList));
    }

    @Test
    public void sortPlayerListTest() throws Exception {
        ITeam unsortedTeam = tradeMock.getTeamWithGoodPlayer();
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
