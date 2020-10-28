package dhl.tradingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.TradeConfigMock;
import dhl.leagueModel.Coach;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import dhl.trading.GenerateTradeOffer;
import dhl.trading.Interface.ITradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GenerateTradeOfferTest {

    GenerateTradeOffer testClassObject;
    LeagueObjectModelMocks leagueMockObject;
    TradeConfigMock tradeConfigMock;
    ITeam goodTeam,badTeam;
    IPlayer goodPlayer,badPlayer;


    @BeforeEach
    public void initObject(){

        leagueMockObject = new LeagueObjectModelMocks();
        tradeConfigMock = new TradeConfigMock();
        testClassObject = new GenerateTradeOffer(tradeConfigMock.getTradeConfig());
        makeTeams();

    }

    public void makeTeams(){

        IPlayerStatistics goodStat = new PlayerStatistics(24, 9,7,10,1);
        IPlayerStatistics badStat = new PlayerStatistics(24,2,2,2,1);
        goodPlayer = new Player("goodPlayer" , "defense", false, goodStat);
        badPlayer = new Player("badPlayer", "defense", false, badStat);
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList = leagueMockObject.getPlayerArrayMock();
        playerList.add(goodPlayer);
        goodTeam = new Team("goodTeam", "goodManager",goodCoach,playerList);
        ArrayList<IPlayer> playerList2 = leagueMockObject.getPlayerArrayMock();
        playerList2.add(badPlayer);
        badTeam = new Team("BadTeam", "goodManager",goodCoach,playerList2);

    }
    @Test
    public void makeTradeTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = leagueMockObject.getLeagueObjectMock();
        IConference conferenceToAddGoodTeam = leagueObjectModel.getConferences().get(0);
        IDivision divisionToAddGoodTeam = conferenceToAddGoodTeam.getDivisions().get(0);
        divisionToAddGoodTeam.getTeams().add(goodTeam);
        badTeam.setLossPoint(10);
        System.out.println("bad team");
        testClassObject.makeTrade(badTeam , leagueObjectModel);
        Assertions.assertTrue(badTeam.getLossPoint() == 0);
        Assertions.assertTrue( badTeam.getPlayers().contains(goodPlayer));
        Assertions.assertFalse( badTeam.getPlayers().contains(badPlayer));

    }


    @Test
    public void sortPlayerListTest() throws Exception {
        ICoach goodCoach = new Coach();
        ArrayList<IPlayer> playerList = leagueMockObject.getPlayerArrayMock();
        playerList.add(goodPlayer);
        playerList.add(badPlayer);
        ITeam team = new Team("goodTeam", "goodManager",goodCoach,playerList);

        ArrayList<IPlayer> players = testClassObject.sortPlayerList(team);
        Assertions.assertTrue(players.get(0).getPlayerStrength() <= players.get(1).getPlayerStrength());

        ITeam empytyPlayerTeam = new Team();
        empytyPlayerTeam.setTeamName("EmptyPlayers");

        Exception error = Assertions.assertThrows(Exception.class,() ->{
            testClassObject.sortPlayerList(empytyPlayerTeam);
        });
        Assertions.assertTrue(error.getMessage().contains("EmptyPlayers Team have no players"));
    }

    @Test
    public void makeSwapTradeOfferTest() throws Exception{
        ITradeOffer noTradeMade = testClassObject.makeSwapTradeOffer(leagueMockObject.getTeamObjectMock() , leagueMockObject.getTeamObjectMock());
        Assertions.assertTrue(noTradeMade == null);
        ITradeOffer tradeOfferWillCome = testClassObject.makeSwapTradeOffer(badTeam, goodTeam);
        Assertions.assertTrue(tradeOfferWillCome != null);
    }
}
