package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.AiAiTrade;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AiAiTradeTest {

    AiAiTrade testClassObject;
    GameConfigMockForTrading gameConfigMock;
    IGameConfig ourGameConfig;
    ILeagueObjectModel league;
    ArrayList<IPlayer> playersOffered, playersWanted;
    ITeam strongTeam, weakTeam;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;

    LeagueModelMockAbstractFactory leagueMockFactory;
    TradeMockAbstractFactory tradeMockFactory;

    @BeforeEach
    public void initObject() {
        playersOffered = new ArrayList<>();
        playersWanted = new ArrayList<>();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();

        league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        ourGameConfig = gameConfigMock.getGameConfigMock();
        strongTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        weakTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        playersOffered.add(weakTeam.getPlayers().get(0));
        playersWanted.add(strongTeam.getPlayers().get(0));
    }

    @Test
    public void isTradeAcceptedTest(){
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(league, ourGameConfig);

        boolean unfairTradeThatWillBeAccepted = testClassObject.isTradeAccepted(playersOffered, playersWanted, strongTeam);
        Assertions.assertTrue(unfairTradeThatWillBeAccepted);

        boolean fairTradeThatWillBeAccepted = testClassObject.isTradeAccepted(playersWanted, playersOffered, weakTeam);
        Assertions.assertTrue(fairTradeThatWillBeAccepted);

        gameConfigMock.setRandomAcceptanceChance(1.0);
        ourGameConfig = gameConfigMock.getGameConfigMock();
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(league, ourGameConfig);

        boolean unfairTradeThatWillBeRejected = testClassObject.isTradeAccepted(playersOffered, playersWanted, strongTeam);
        Assertions.assertFalse(unfairTradeThatWillBeRejected);
    }

    @Test
    public void validateTeamRosterAfterTradeTest() {
        ArrayList<IPlayer> freeAgents = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.freeAgents = freeAgents;
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics( 10, 10, 10, 10);
        playerStatistics.setAge(25);
        IPlayer player = leagueFactory.createPlayer("player1", "goalie", false, playerStatistics);
        team.getPlayers().add(player);

        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(league, ourGameConfig);

        testClassObject.validateTeamRosterAfterTrade(team);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());
    }

    @Test
    public void isTradeGoodForReceivingTeamTest() {
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(league, ourGameConfig);
        Assertions.assertFalse(testClassObject.isTradeGoodForReceivingTeam(playersOffered, playersWanted));

        Assertions.assertTrue(testClassObject.isTradeGoodForReceivingTeam(playersWanted, playersOffered));
    }

    @Test
    public void getPlayerCombinedStrengthTest() {
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("player1", PlayerPosition.DEFENSE.toString()));
        players.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("player2", PlayerPosition.DEFENSE.toString()));

        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(league, ourGameConfig);
        Assertions.assertEquals(testClassObject.getPlayerCombinedStrength(players), 50.0);
    }

}
