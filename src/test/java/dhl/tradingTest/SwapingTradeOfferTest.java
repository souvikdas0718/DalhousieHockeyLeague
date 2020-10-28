package dhl.tradingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.Coach;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trading.Interface.ITradeOffer;
import dhl.trading.SwapingTradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SwapingTradeOfferTest {
    
    ArrayList<IPlayer> strongPlayers,weakPlayers ;
    ICoach headCoach;
    ITradeOffer goodTrade,trade;
    LeagueObjectModelMocks leagueMockObject;
    @BeforeEach
    public void initObject(){
        leagueMockObject = new LeagueObjectModelMocks();
        strongPlayers = new ArrayList<>();
        strongPlayers.add(new Player("Strong player", "defense", false,
                new PlayerStatistics(25,10,10,10,0)
        ));
        strongPlayers.add(new Player("Strong player 2", "defense", false,
                new PlayerStatistics(25,10,10,10,0)
        ));
        weakPlayers = new ArrayList<>();
        weakPlayers.add(new Player("WeakPlayer", "defense", false,
                new PlayerStatistics(38,1,1,1,0)
        ));
        weakPlayers.add(new Player("WeakPlayer2", "defense", false,
                new PlayerStatistics(38,2,2,2,0)
        ));
        ArrayList<IPlayer> weakPlayertoSwap = new ArrayList<>();
        weakPlayertoSwap.add(weakPlayers.get(0));
        ArrayList<IPlayer> strongPlayertoSwap = new ArrayList<>();
        strongPlayertoSwap.add(strongPlayers.get(0));
        headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam teamA = new Team("Mock Team A", "Mock Manager A", headCoach, strongPlayers );
        ITeam teamB = new Team("Mock Team B", "Mock Manager B", headCoach, weakPlayers );

        goodTrade = new SwapingTradeOffer( teamA,teamB,strongPlayertoSwap,weakPlayertoSwap);
        trade = new SwapingTradeOffer( teamB,teamA,weakPlayertoSwap,strongPlayertoSwap);
    }

    @Test
    public void isTradeGoodForReceivingTeamTest(){
        Assertions.assertFalse(trade.isTradeGoodForReceivingTeam());
        Assertions.assertTrue(goodTrade.isTradeGoodForReceivingTeam());
    }

    @Test
    public void getPlayerCombinedStrengthTest(){
        double goodTeamStrength,weekTeamStrength;
        goodTeamStrength = trade.getPlayerCombinedStrength(strongPlayers);
        weekTeamStrength = trade.getPlayerCombinedStrength(weakPlayers);
        Assertions.assertTrue(goodTeamStrength > weekTeamStrength);

    }

    @Test
    public void getOfferingTeamTest() {
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList2 = leagueMockObject.getPlayerArrayMock();
        ITeam team = new Team("TestTeam", "goodManager",goodCoach,playerList2);
        trade.setOfferingTeam(team);
        Assertions.assertTrue(trade.getOfferingTeam().getTeamName() == "TestTeam");
    }

    @Test
    public void setOfferingTeamTest() {
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList2 = leagueMockObject.getPlayerArrayMock();
        ITeam team = new Team("TestTeam", "goodManager",goodCoach,playerList2);
        trade.setOfferingTeam(team);
        Assertions.assertTrue(trade.getOfferingTeam().getTeamName() == "TestTeam");
    }

    @Test
    public void getReceivingTeamTest() {
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList2 = leagueMockObject.getPlayerArrayMock();
        ITeam team = new Team("TestTeam", "goodManager",goodCoach,playerList2);
        trade.setReceivingTeam(team);
        Assertions.assertTrue(trade.getReceivingTeam().getTeamName() == "TestTeam");
    }

    @Test
    public void setReceivingTeamTest() {
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList2 = leagueMockObject.getPlayerArrayMock();
        ITeam team = new Team("TestTeam", "goodManager",goodCoach,playerList2);
        trade.setReceivingTeam(team);
        Assertions.assertTrue(trade.getReceivingTeam().getTeamName() == "TestTeam");
    }

    @Test
    public void getPlayersOfferedTest() {
        ArrayList<IPlayer> offeredPlayers = trade.getPlayersOffered();
        Assertions.assertTrue(offeredPlayers.get(0).getPlayerName()=="WeakPlayer");
    }

    @Test
    public void setPlayersOfferedTest() {
        trade.setPlayersOffered(leagueMockObject.getPlayerArrayMock());
        ArrayList<IPlayer> players = trade.getPlayersOffered();
        Assertions.assertTrue(players.get(0).getPlayerName()=="Mock Player");
    }

    @Test
    public void getPlayersWantedInExchangeTest() {
        ArrayList<IPlayer> players = trade.getPlayersWantedInExchange();
        Assertions.assertTrue(players.get(0).getPlayerName()=="Strong player");

    }

    @Test
    public void setPlayersWantedInExchangeTest() {
        trade.setPlayersWantedInExchange(leagueMockObject.getPlayerArrayMock());
        ArrayList<IPlayer> players = trade.getPlayersWantedInExchange();
        Assertions.assertTrue(players.get(0).getPlayerName()=="Mock Player");
    }
}
