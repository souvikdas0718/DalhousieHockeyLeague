package dhl.tradeTest;

import dhl.Mocks.GameConfigMock;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.AiAiTrade;
import dhl.trade.ExchangingPlayerTradeOffer;
import dhl.trade.Interface.ITradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiAiTradeTest {

    AiAiTrade testClassObject;
    TradeMock tradeMock;
    GameConfigMock gameConfigMock;
    IGameConfig ourGameConfig;
    ArrayList<IPlayer> playersOffered,playersWanted;
    ITeam strongTeam,weakTeam;
    LeagueObjectModelMocks leagueObjectModelMocks;

    @BeforeEach
    public void initObject(){
        tradeMock = new TradeMock();
        gameConfigMock = new GameConfigMock();
        ourGameConfig = gameConfigMock.getGameConfigMock();
        strongTeam = tradeMock.getTeamWithGoodPlayer();
        weakTeam = tradeMock.getTeamWithBadPlayer();
        playersOffered = new ArrayList<>();
        playersOffered.add(weakTeam.getPlayers().get(0));
        playersWanted = new ArrayList<>();
        playersWanted.add(strongTeam.getPlayers().get(0));
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void isTradeAcceptedTest(){
        ITradeOffer tradeNotAccepted = new ExchangingPlayerTradeOffer(weakTeam , strongTeam , playersOffered,playersWanted);
        testClassObject = new AiAiTrade(tradeNotAccepted , ourGameConfig);

        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);

        Assertions.assertTrue(testClassObject.isTradeAccepted());
    }

    @Test
    public void validateTeamRosterAfterTradeTest() throws Exception {
        ArrayList<IPlayer> freeAgents = tradeMock.get50FreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
        league.freeAgents = freeAgents;
        ITeam team = tradeMock.getTeamWithGoodPlayer();
        IPlayer player = new Player("player1", "goalie", false,
                new PlayerStatistics(25,10,10,10,10));
        team.getPlayers().add(player);
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);

        testClassObject.validateTeamRosterAfterTrade(team , league);
        Assertions.assertTrue(team.checkIfSkatersGoaliesValid());
    }

    @Test
    public void updateSkatersTest() throws Exception {
        ArrayList<IPlayer> freeAgents = tradeMock.get50FreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
        league.freeAgents = freeAgents;
        ITeam team = tradeMock.getTeamWithGoodPlayer();
        for(int i = 0 ; i < 10 ; i++){
            team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer"+ i));
        }
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);

        testClassObject.updateSkaters(team.getPlayers().size() , team , league);
        Assertions.assertTrue(team.getPlayers().size() == 18);

        team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer"));

        testClassObject.updateSkaters(team.getPlayers().size() , team , league);
        Assertions.assertTrue(team.getPlayers().size() == 18);

    }

    @Test
    public void findBestSkaterTest() throws Exception {
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);
        ArrayList<IPlayer> playerList = new ArrayList<>();

        Exception error=Assertions.assertThrows(Exception.class,() ->{
            testClassObject.findBestSkater(playerList);
        });
        Assertions.assertTrue(error.getMessage().contains("No Skater found in List"));

        playerList.add(tradeMock.getStrongPlayer("strongPlayer"));
        playerList.add(tradeMock.getWeakPlayer("weakPlayer"));
        IPlayer returnedPlayer = testClassObject.findBestSkater(playerList);
        Assertions.assertTrue(returnedPlayer.getPlayerName().equals("strongPlayer"));
    }

    @Test
    public void findWeakSkaterTest() throws Exception {
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);
        ArrayList<IPlayer> playerList = new ArrayList<>();

        Exception error=Assertions.assertThrows(Exception.class,() ->{
            testClassObject.findWeakSkater(playerList);
        });
        Assertions.assertTrue(error.getMessage().contains("No Skater found in List"));

        playerList.add(tradeMock.getStrongPlayer("strongPlayer"));
        playerList.add(tradeMock.getWeakPlayer("weakPlayer"));

        IPlayer returnedPlayer = testClassObject.findWeakSkater(playerList);
        Assertions.assertTrue(returnedPlayer.getPlayerName().equals("weakPlayer"));
    }

    @Test
    public void updateGoaliesTest() throws Exception {
        ArrayList<IPlayer> freeAgents = tradeMock.get50FreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
        league.freeAgents = freeAgents;

        ITeam team = tradeMock.getTeamWithGoodPlayer();
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);
        int sizeOfTeamWithoutGolie = team.getPlayers().size();
        testClassObject.updateGoalies(0 , team , league);
        Assertions.assertTrue(team.getPlayers().size() == sizeOfTeamWithoutGolie+2);

        IPlayer player = new Player("player1", "goalie", false,
                new PlayerStatistics(25,10,10,10,10));
        team.getPlayers().add(player);
        int teamSizeWith3Golie = team.getPlayers().size();
        testClassObject.updateGoalies(3 , team , league);

        Assertions.assertTrue(team.getPlayers().size() == teamSizeWith3Golie-1);
    }

    @Test
    public void findBestGolieTest() throws Exception {
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);
        ArrayList<IPlayer> playerList = new ArrayList<>();

        Exception error=Assertions.assertThrows(Exception.class,() ->{
            testClassObject.findBestGolie(playerList);
        });
        Assertions.assertTrue(error.getMessage().contains("No Golie found in List"));

        IPlayer player = new Player("player1", "goalie", false,
                new PlayerStatistics(25,10,10,10,10));
        playerList.add(player);
        player = new Player("player2", "goalie", false,
                new PlayerStatistics(25,3,1,4,5));
        playerList.add(player);
        playerList.add(tradeMock.getWeakPlayer("weakPlayer"));
        IPlayer returnedPlayer = testClassObject.findBestGolie(playerList);
        Assertions.assertTrue(returnedPlayer.getPlayerName().equals("player1"));
    }

    @Test
    public void findWeakGolieTest() throws Exception {
        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);
        ArrayList<IPlayer> playerList = new ArrayList<>();

        Exception error=Assertions.assertThrows(Exception.class,() ->{
            testClassObject.findWeakGolie(playerList);
        });
        Assertions.assertTrue(error.getMessage().contains("No Golie found in List"));

        IPlayer player = new Player("player1", "goalie", false,
                new PlayerStatistics(25,10,10,10,10));
        playerList.add(player);
        player = new Player("player2", "goalie", false,
                new PlayerStatistics(25,3,1,4,5));
        playerList.add(player);
        playerList.add(tradeMock.getWeakPlayer("weakPlayer"));
        IPlayer returnedPlayer = testClassObject.findWeakGolie(playerList);
        Assertions.assertTrue(returnedPlayer.getPlayerName().equals("player2"));
    }

    @Test
    public void isTradeGoodForReceivingTeamTest(){
        ITradeOffer testOffer = new ExchangingPlayerTradeOffer(weakTeam,strongTeam,playersOffered,playersWanted);
        testClassObject = new AiAiTrade(testOffer , ourGameConfig);
        Assertions.assertFalse(testClassObject.isTradeGoodForReceivingTeam(testOffer));

        ITeam goodTeam = tradeMock.getTeamWithGoodPlayer();
        ITeam badTeam = tradeMock.getTeamWithBadPlayer();
        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        offeringPlayers.add(goodTeam.getPlayers().get(0));
        ArrayList<IPlayer> receivingPlayers = new ArrayList<>();
        receivingPlayers.add(badTeam.getPlayers().get(0));
        ExchangingPlayerTradeOffer goodTradeForReceiver = new ExchangingPlayerTradeOffer(goodTeam,badTeam,offeringPlayers,receivingPlayers );
        testClassObject = new AiAiTrade(goodTradeForReceiver , ourGameConfig);
        Assertions.assertTrue(testClassObject.isTradeGoodForReceivingTeam(goodTradeForReceiver));
    }

    @Test
    public void getPlayerCombinedStrengthTest(){
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(tradeMock.getStrongPlayer("player1"));
        players.add(tradeMock.getStrongPlayer("player2"));

        ITradeOffer testOffer = new ExchangingPlayerTradeOffer(weakTeam,strongTeam,playersOffered,playersWanted);
        testClassObject = new AiAiTrade(testOffer , ourGameConfig);
        Assertions.assertTrue(testClassObject.getPlayerCombinedStrength(players) == 50.0);
    }
}
