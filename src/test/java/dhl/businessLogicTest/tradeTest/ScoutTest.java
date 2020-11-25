package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.Scout;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoutTest {

    Scout testClassObject;
    IGameConfig ourGameConfig;

    TradeAbstractFactory tradeFactory;
    LeagueModelMockAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;
    GameConfigMockForTrading gameConfigMock;

    @BeforeEach
    public void initObject(){
        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelMockAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();

        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        ourGameConfig = gameConfigMock.getGameConfigMock();
    }

    @Test
    public void findWeakPartOfTeamTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ITeam weakDefenceTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.DEFENSE.toString());
        ITeam weakForwardTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.FORWARD.toString());

        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig);

        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakForwardTeam) , PlayerPosition.FORWARD.toString());
        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakDefenceTeam) , PlayerPosition.DEFENSE.toString());
        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakGoalieTeam) , PlayerPosition.GOALIE.toString());
    }

    @Test
    public void getWeakPlayerTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig);

        IPlayer testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.GOALIE.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.GOALIE.toString());

        testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.DEFENSE.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.DEFENSE.toString());

        testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.FORWARD.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.FORWARD.toString());


    }

    @Test
    public void isTeamGoodForTradingTest() throws Exception {
        ITeam badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        ITeam goodTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject = (Scout) tradeFactory.createScout(badTeamMock, league, ourGameConfig);

        Assertions.assertTrue(testClassObject.isTeamGoodForTrading(badTeamMock, goodTeamMock));
        Assertions.assertFalse(testClassObject.isTeamGoodForTrading(goodTeamMock, badTeamMock));
    }
}
