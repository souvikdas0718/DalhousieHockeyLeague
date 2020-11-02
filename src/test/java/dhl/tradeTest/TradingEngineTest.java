package dhl.tradeTest;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.TradingEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.ArrayList;

public class TradingEngineTest {

    TradingEngine testClassObject;
    GameConfigMock gameConfigMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    TradeMock tradeMock;
    ITeam goodTeamMock,badTeamMock;
    ILeagueObjectModel leagueMock;
    IUserInputOutput ioObject;
    ITeam userTeam;

    @BeforeEach
    public void initObject(){
        ioObject = new UserInputOutput();
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
        testClassObject = new TradingEngine(ourGameConfig ,leagueMock,userTeam,ioObject);
    }

    @Test
    public void performTradeTest() throws Exception {
        testClassObject.performTrade(goodTeamMock);
        System.out.println(goodTeamMock.getLossPoint());
        Assertions.assertTrue(goodTeamMock.getLossPoint() > 0);
        testClassObject.performTrade(badTeamMock);
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    @Test
    public void sendTradeToRecevingTeamTest(){
        // TODO: 01-11-2020 check this
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

        Exception error=Assertions.assertThrows(Exception.class,() ->{
            testClassObject.findTeamToTradeWith(goodTeamMock);
        });
        Assertions.assertTrue(error.getMessage().contains("No Good Player availabe to swap for Team: "));

    }

    @Test
    public void generateTradeOfferTest() throws Exception {
        ITradeOffer tradeOffer = testClassObject.generateTradeOffer(badTeamMock , goodTeamMock);
        Assertions.assertFalse(tradeOffer == null);
        Assertions.assertTrue( tradeOffer.getOfferingTeam() == badTeamMock);
        Assertions.assertTrue(tradeOffer.getReceivingTeam() == goodTeamMock);
    }

    @Test
    public void sortPlayerListTest() throws Exception {
        ITeam unsortedTeam = tradeMock.getTeamWithGoodPlayer();
        IPlayer player = new Player("PlayerA", "defense", false,
                new PlayerStatistics(25,5,3,2,1));
        unsortedTeam.getPlayers().add(player);
        player = new Player("AnotherPlayer", "defense", false,
                new PlayerStatistics(34,7,3,9,7));
        unsortedTeam.getPlayers().add(player);
        testClassObject.sortPlayerList(unsortedTeam);
        Assertions.assertTrue(unsortedTeam.getPlayers().get(0).getPlayerStrength() <= unsortedTeam.getPlayers().get(1).getPlayerStrength());


        Team empytyPlayerTeam = new Team("EmptyPlayers","Larry",new Coach(),new ArrayList<>());

        Exception error = Assertions.assertThrows(Exception.class,() ->{
            testClassObject.sortPlayerList(empytyPlayerTeam);
        });
        Assertions.assertTrue(error.getMessage().contains("EmptyPlayers Team have no players"));
    }

    @Test
    public void isTeamDifferentTest(){
        Team newTeam = new Team("team1","Larry",new Coach(),new ArrayList<>());
        Assertions.assertFalse(testClassObject.isTeamDifferent(newTeam , newTeam));
        Team otherTeam = new Team("secondTeam","Larry",new Coach(),new ArrayList<>());
        Assertions.assertTrue(testClassObject.isTeamDifferent(newTeam,otherTeam));
    }

    @Test
    public void isTeamGoodForTradingTest() throws Exception {
        Assertions.assertTrue(testClassObject.isTeamGoodForTrading(badTeamMock ,goodTeamMock));
        Assertions.assertFalse(testClassObject.isTeamGoodForTrading(goodTeamMock, badTeamMock));
    }
}
