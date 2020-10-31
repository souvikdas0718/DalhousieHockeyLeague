package dhl.tradeTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.PlayerSwappingTradeEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerSwappingTradeEngineTest {

    PlayerSwappingTradeEngine testClassObject;
    GameConfigMock gameConfigMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    TradeMock tradeMock;
    ITeam goodTeamMock,badTeamMock;
    ILeagueObjectModel leagueMock;

    @BeforeEach
    public void initObject(){
        tradeMock = new TradeMock();
        gameConfigMock = new GameConfigMock();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        goodTeamMock = tradeMock.getTeamWithGoodPlayer();
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        leagueMock = leagueObjectModelMocks.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        IGameConfig ourGameConfig = gameConfigMock.getGameConfigMock();
        ourGameConfig.setRequiredObjectFromConfig("trading");
        testClassObject = new PlayerSwappingTradeEngine(ourGameConfig ,leagueMock);
    }

    @Test
    public void makeOfferTest() throws Exception {
        testClassObject.makeOffer(badTeamMock);
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
        testClassObject.makeOffer(goodTeamMock);
        Assertions.assertTrue(goodTeamMock.getLossPoint() > 0);
    }

    @Test
    public void isObjectInitiatedTest(){
        Object object = null;
        Assertions.assertFalse(testClassObject.isObjectInitiated(object));
        object = new Object();
        Assertions.assertTrue(testClassObject.isObjectInitiated(object));
    }

    @Test
    public void isTeamDifferentTest(){
        Team newTeam = new Team("team1","Larry",new Coach(),new ArrayList<>());
        Assertions.assertFalse(testClassObject.isTeamDifferent(newTeam , newTeam));
        Team otherTeam = new Team("secondTeam","Larry",new Coach(),new ArrayList<>());
        Assertions.assertTrue(testClassObject.isTeamDifferent(newTeam,otherTeam));
    }

    @Test
    public void ifTradePossibleMakeOfferTest() throws Exception {
        Assertions.assertTrue(testClassObject.ifTradePossibleMakeOffer(goodTeamMock , badTeamMock) == null);
        Assertions.assertFalse(testClassObject.ifTradePossibleMakeOffer(badTeamMock , goodTeamMock) == null);
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
}
