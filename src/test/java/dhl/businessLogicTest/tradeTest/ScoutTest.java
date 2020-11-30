package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.AiAiTrade;
import dhl.businessLogic.trade.AiUserTrade;
import dhl.businessLogic.trade.Scout;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.TradeOfferAbstract;
import dhl.businessLogic.trade.interfaces.ITradeType;
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
    public void findTradeTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        TradeOfferAbstract tradeOffer = null;
        tradeOffer = testClassObject.findTrade(1);
        Assertions.assertEquals(tradeOffer.getPlayersWantedInReturn().size(), 1);

        tradeOffer = testClassObject.findTrade(2);
        int playerInTrade = tradeOffer.getPlayersWantedInReturn().size();
        Assertions.assertEquals(playerInTrade , 1);

        tradeOffer = testClassObject.findTrade(3);
        playerInTrade = tradeOffer.getPlayersWantedInReturn().size();
        Assertions.assertEquals(playerInTrade , 2);

        tradeOffer = testClassObject.findTrade(4);
        playerInTrade = tradeOffer.getPlayersWantedInReturn().size();
        Assertions.assertEquals(playerInTrade , 2);
    }

    @Test
    public void getTradeTypeObjectTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        ITradeType tradeType = testClassObject.getTradeTypeObject(userTeam);
        Assertions.assertTrue(tradeType instanceof AiUserTrade);

        tradeType = testClassObject.getTradeTypeObject(weakGoalieTeam);
        Assertions.assertTrue(tradeType instanceof AiAiTrade);
    }

    @Test
    public void getPlayerToTradeFromTeamTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        IPlayer player = testClassObject.getPlayerToTradeFromTeam(weakGoalieTeam, PlayerPosition.DEFENSE.toString(), 1.0 );
        Assertions.assertEquals(player.getPosition(), PlayerPosition.DEFENSE.toString());

        player = testClassObject.getPlayerToTradeFromTeam(weakGoalieTeam, PlayerPosition.FORWARD.toString(), 1.0);
        Assertions.assertEquals(player.getPosition(), PlayerPosition.FORWARD.toString());

        player = testClassObject.getPlayerToTradeFromTeam(weakGoalieTeam, PlayerPosition.GOALIE.toString(), 1.0);
        Assertions.assertEquals(player.getPosition(), PlayerPosition.GOALIE.toString());

        player = testClassObject.getPlayerToTradeFromTeam(weakGoalieTeam, PlayerPosition.GOALIE.toString(), 100.0);
        Assertions.assertEquals(player, null);
    }
    
    @Test
    public void findWeakPartOfTeamTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ITeam weakDefenceTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.DEFENSE.toString());
        ITeam weakForwardTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.FORWARD.toString());

        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakForwardTeam) , PlayerPosition.FORWARD.toString());
        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakDefenceTeam) , PlayerPosition.DEFENSE.toString());
        Assertions.assertEquals(testClassObject.findWeakPartOfTeam(weakGoalieTeam) , PlayerPosition.GOALIE.toString());
    }

    @Test
    public void getWeakPlayerTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        IPlayer testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.GOALIE.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.GOALIE.toString());

        testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.DEFENSE.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.DEFENSE.toString());

        testPlayer = testClassObject.getWeakPlayer(weakGoalieTeam, PlayerPosition.FORWARD.toString());
        Assertions.assertEquals(testPlayer.getPosition(), PlayerPosition.FORWARD.toString());
    }

    @Test
    public void findTeamToTradeWithTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        IPlayer player = tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("weak", PlayerPosition.DEFENSE.toString() );
        ITeam team = testClassObject.findTeamToTradeWith(league, PlayerPosition.GOALIE.toString(), player);
        Assertions.assertFalse(team == null);

        player = tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("strong", PlayerPosition.DEFENSE.toString() );
        team = testClassObject.findTeamToTradeWith(league, PlayerPosition.GOALIE.toString(), player);
        Assertions.assertTrue(team == null);
    }

    @Test
    public void playerFoundTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        Assertions.assertFalse(testClassObject.playerFound(null));
        Assertions.assertTrue( testClassObject.playerFound(weakGoalieTeam.getPlayers().get(0)));
    }

    @Test
    public void teamFoundTest(){
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        Assertions.assertFalse(testClassObject.teamFound(null));
        Assertions.assertTrue( testClassObject.teamFound(weakGoalieTeam));
    }

    @Test
    public void findPlayerToTrade() {
        ITeam weakGoalieTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPosition(PlayerPosition.GOALIE.toString());
        ILeagueObjectModel league = leagueFactory.createLeagueMock().getLeagueObjectModel();
        ITeam userTeam = leagueFactory.createTeamMock().getTeam();
        testClassObject = (Scout) tradeFactory.createScout(weakGoalieTeam, league,ourGameConfig,userTeam);

        IPlayer player = testClassObject.findPlayerToTrade(weakGoalieTeam, PlayerPosition.DEFENSE.toString(), 1);
        Assertions.assertEquals(player.getPosition(), PlayerPosition.DEFENSE.toString());

        player = testClassObject.findPlayerToTrade(weakGoalieTeam, PlayerPosition.FORWARD.toString(), 1);
        Assertions.assertEquals(player.getPosition(), PlayerPosition.FORWARD.toString());

        player = testClassObject.findPlayerToTrade(weakGoalieTeam, PlayerPosition.GOALIE.toString(), 1);
        Assertions.assertEquals(player.getPosition(), PlayerPosition.GOALIE.toString());

        player = testClassObject.findPlayerToTrade(weakGoalieTeam, PlayerPosition.GOALIE.toString(), 100);
        Assertions.assertEquals(player, null);
    }
}
