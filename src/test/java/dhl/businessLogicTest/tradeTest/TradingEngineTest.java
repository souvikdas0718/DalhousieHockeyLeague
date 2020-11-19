package dhl.businessLogicTest.tradeTest;

import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.MockUserInputOutput;
import dhl.businessLogic.leagueModel.Coach;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.simulationStateMachine.UpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.TradingEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class TradingEngineTest {

    TradingEngine testClassObject;
    GameConfigMock gameConfigMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    TradeMock tradeMock;
    ITeam goodTeamMock, badTeamMock;
    ILeagueObjectModel leagueMock;
    IUserInputOutput ioObject;
    ITeam userTeam;

    @BeforeEach
    public void initObject() {
        ioObject = new MockUserInputOutput();
        tradeMock = new TradeMock();
        gameConfigMock = new GameConfigMock();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        goodTeamMock = tradeMock.getTeamWithGoodPlayer();
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        leagueMock = leagueObjectModelMocks.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        IGameConfig ourGameConfig = gameConfigMock.getGameConfigMock();
        userTeam = new Team();
        IUpdateUserTeamRoster updateUserTeamRoster = new UpdateUserTeamRoster(ioObject);
        testClassObject = new TradingEngine(ourGameConfig, leagueMock, userTeam, ioObject, updateUserTeamRoster);
    }

    @Test
    public void startEngine() {
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        testClassObject.startEngine();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());

    }

    @Test
    public void performTradeTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        testClassObject.performTrade(goodTeamMock);

        Assertions.assertTrue(outContent.toString().contains("No Good Player availabe to swap for Team:"));
        Assertions.assertTrue(goodTeamMock.getLossPoint() > 0);

        testClassObject.performTrade(badTeamMock);
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    @Test
    public void sendTradeToRecevingTeamTest() throws Exception {
        ITradeOffer tradeOffer = testClassObject.generateTradeOffer(badTeamMock, goodTeamMock);
        double teamStrength = badTeamMock.calculateTeamStrength();

        ((MockUserInputOutput) ioObject).setMockOutput("2");
        testClassObject.sendTradeToRecevingTeam(tradeOffer, tradeOffer.getReceivingTeam());
        Assertions.assertTrue(teamStrength == badTeamMock.calculateTeamStrength());

        ((MockUserInputOutput) ioObject).setMockOutput("1");
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.sendTradeToRecevingTeam(tradeOffer, tradeOffer.getReceivingTeam());
        });

        Assertions.assertTrue(teamStrength < badTeamMock.calculateTeamStrength());
        Assertions.assertFalse(teamStrength == badTeamMock.calculateTeamStrength());
    }

    @Test
    public void getCurrentTradeTest() throws Exception {
        testClassObject.performTrade(badTeamMock);
        ITradeOffer tradeOffer = testClassObject.getCurrentTrade();
        Assertions.assertTrue(tradeOffer.getOfferingTeam() == badTeamMock);
    }

    @Test
    public void findTeamToTradeWithTest() throws Exception {
        ITeam team = testClassObject.findTeamToTradeWith(badTeamMock);
        Assertions.assertTrue(team.getTeamName().equals("TeamWithGoodPlayer"));

        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.findTeamToTradeWith(goodTeamMock);
        });
        Assertions.assertTrue(error.getMessage().contains("No Good Player availabe to swap for Team: "));

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
        IPlayer player = new Player("PlayerA", "defense", false,
                new PlayerStatistics(25, 5, 3, 2, 1));
        unsortedTeam.getPlayers().add(player);
        player = new Player("AnotherPlayer", "defense", false,
                new PlayerStatistics(34, 7, 3, 9, 7));
        unsortedTeam.getPlayers().add(player);
        testClassObject.sortPlayerList(unsortedTeam);
        Assertions.assertTrue(unsortedTeam.getPlayers().get(0).getPlayerStrength() <= unsortedTeam.getPlayers().get(1).getPlayerStrength());


        Team empytyPlayerTeam = new Team("EmptyPlayers", "Larry", new Coach(), new ArrayList<>());

        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.sortPlayerList(empytyPlayerTeam);
        });
        Assertions.assertTrue(error.getMessage().contains("EmptyPlayers Team have no players"));
    }

    @Test
    public void isTeamDifferentTest() {
        Team newTeam = new Team("team1", "Larry", new Coach(), new ArrayList<>());
        Assertions.assertFalse(testClassObject.isTeamDifferent(newTeam, newTeam));
        Team otherTeam = new Team("secondTeam", "Larry", new Coach(), new ArrayList<>());
        Assertions.assertTrue(testClassObject.isTeamDifferent(newTeam, otherTeam));
    }

    @Test
    public void isTeamGoodForTradingTest() throws Exception {
        Assertions.assertTrue(testClassObject.isTeamGoodForTrading(badTeamMock, goodTeamMock));
        Assertions.assertFalse(testClassObject.isTeamGoodForTrading(goodTeamMock, badTeamMock));
    }
}
