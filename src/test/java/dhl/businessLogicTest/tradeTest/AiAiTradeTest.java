package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.*;
import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.AiAiTrade;
import dhl.businessLogic.trade.ExchangingPlayerTradeOffer;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class AiAiTradeTest {

    AiAiTrade testClassObject;
    TradeMock tradeMock;
    GameConfigMock gameConfigMock;
    IGameConfig ourGameConfig;
    ArrayList<IPlayer> playersOffered, playersWanted;
    ITeam strongTeam, weakTeam;
    LeagueObjectModelMocks leagueObjectModelMocks;
    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach
    public void initObject() {
        playersOffered = new ArrayList<>();
        playersWanted = new ArrayList<>();
        tradeMock = new TradeMock();
        gameConfigMock = new GameConfigMock();
        leagueObjectModelMocks = new LeagueObjectModelMocks();

        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();

        ourGameConfig = gameConfigMock.getGameConfigMock();
        strongTeam = tradeMock.getTeamWithGoodPlayer();
        weakTeam = tradeMock.getTeamWithBadPlayer();
        playersOffered.add(weakTeam.getPlayers().get(0));
        playersWanted.add(strongTeam.getPlayers().get(0));
    }

    @Test
    public void isTradeAcceptedTest(){

        ITradeOffer unfairTradeAccepted = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(unfairTradeAccepted, ourGameConfig);
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        ITradeOffer tradeAccepted = tradeFactory.createExchangingPlayerTradeOffer(strongTeam, weakTeam, playersWanted, playersOffered);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(tradeAccepted, ourGameConfig);
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        gameConfigMock.setRandomAcceptanceChance(1.0);
        ourGameConfig = gameConfigMock.getGameConfigMock();

        ITradeOffer tradeNotAccepted = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject =(AiAiTrade) tradeFactory.createAiAiTrade(tradeNotAccepted, ourGameConfig);
        Assertions.assertFalse(testClassObject.isTradeAccepted());
    }

    @Test
    public void validateTeamRosterAfterTradeTest() throws Exception {
        ArrayList<IPlayer> freeAgents = tradeMock.get50FreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
        league.freeAgents = freeAgents;
        ITeam team = tradeMock.getTeamWithGoodPlayer();

        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(25, 10, 10, 10, 10);
        IPlayer player = leagueFactory.createPlayer("player1", "goalie", false, playerStatistics);
        team.getPlayers().add(player);

        ITradeOffer acceptedTrade = tradeFactory.createExchangingPlayerTradeOffer(strongTeam, weakTeam, playersWanted, playersOffered);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(acceptedTrade, ourGameConfig);

        testClassObject.validateTeamRosterAfterTrade(team, league);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());
    }

    @Test
    public void updatePlayersTest() throws Exception {

        ArrayList<IPlayer> freeAgents = tradeMock.get50FreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
        league.freeAgents = freeAgents;
        ITeam team = tradeMock.getTeamWithGoodPlayer();

        ITradeOffer testOffer = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(testOffer, ourGameConfig);

        int countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        testClassObject.updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),10, team, league);

        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(countDefence, 10);

        testClassObject.updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),5, team, league);
        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(countDefence, 5);

    }

    @Test
    public void findWeakestPlayerInListTest() throws Exception {
        ITradeOffer testOffer = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(testOffer, ourGameConfig);
        ITeam team = tradeMock.getTeamWithBadPlayer();

        IPlayer player = testClassObject.findBestPlayerInList(PlayerPosition.DEFENSE.toString(), team.getPlayers());
        Assertions.assertTrue(player.getPlayerName().contains("Weak"));

        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.findWeakestPlayerInList(PlayerPosition.FORWARD.toString(), team.getPlayers());
        });
        Assertions.assertTrue(error.getMessage().contains("found in List"));
    }

    @Test
    public void findBestPlayerInListtTest() throws Exception {
        ITradeOffer testOffer = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(testOffer, ourGameConfig);
        ITeam team = tradeMock.getTeamWithBadPlayer();

        IPlayer player = testClassObject.findBestPlayerInList(PlayerPosition.DEFENSE.toString(), team.getPlayers());
        Assertions.assertTrue(player.getPlayerName().contains("Weak"));

        Exception error = Assertions.assertThrows(Exception.class, () -> {
            testClassObject.findWeakestPlayerInList(PlayerPosition.FORWARD.toString(), team.getPlayers());
        });
        Assertions.assertTrue(error.getMessage().contains("found in List"));
    }

    @Test
    public void isTradeGoodForReceivingTeamTest() {
        ITradeOffer testOffer = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(testOffer, ourGameConfig);
        Assertions.assertFalse(testClassObject.isTradeGoodForReceivingTeam(testOffer));

        ITeam goodTeam = tradeMock.getTeamWithGoodPlayer();
        ITeam badTeam = tradeMock.getTeamWithBadPlayer();

        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        offeringPlayers.add(goodTeam.getPlayers().get(0));

        ArrayList<IPlayer> receivingPlayers = new ArrayList<>();
        receivingPlayers.add(badTeam.getPlayers().get(0));

        ExchangingPlayerTradeOffer goodTradeForReceiver = (ExchangingPlayerTradeOffer) tradeFactory.createExchangingPlayerTradeOffer(goodTeam, badTeam, offeringPlayers, receivingPlayers);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(goodTradeForReceiver, ourGameConfig);
        Assertions.assertTrue(testClassObject.isTradeGoodForReceivingTeam(goodTradeForReceiver));
    }

    @Test
    public void getPlayerCombinedStrengthTest() {
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(tradeMock.getStrongPlayer("player1"));
        players.add(tradeMock.getStrongPlayer("player2"));

        ITradeOffer testOffer = tradeFactory.createExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
        testClassObject = (AiAiTrade) tradeFactory.createAiAiTrade(testOffer, ourGameConfig);
        Assertions.assertEquals(testClassObject.getPlayerCombinedStrength(players), 50.0);
    }
}
