package dhl.tradeTest;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.ExchangingPlayerTradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ExchangingPlayerTradeOfferTest {

    ExchangingPlayerTradeOffer testClassObject;
    TradeMock mockObject;

    @BeforeEach
    public void initObject(){
        mockObject = new TradeMock();
        ITeam strongTeam = mockObject.getTeamWithGoodPlayer();
        ITeam weakTeam = mockObject.getTeamWithBadPlayer();
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        playersOffered.add(weakTeam.getPlayers().get(0));
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        playersWanted.add(strongTeam.getPlayers().get(0));
        testClassObject = new ExchangingPlayerTradeOffer(weakTeam , strongTeam , playersOffered,playersWanted);
    }

    @Test
    public void performTradeTest(){

        testClassObject.performTrade();
        IPlayer playerNowInStrongTeam = testClassObject.playersOffered.get(0);
        IPlayer playerNowInWeakTeam = testClassObject.playersWantedInExchange.get(0);

        Assertions.assertTrue(testClassObject.getOfferingTeam().getPlayers().contains(playerNowInWeakTeam));
        Assertions.assertTrue(testClassObject.getReceivingTeam().getPlayers().contains(playerNowInStrongTeam));

    }

    @Test
    public void isTradeGoodForReceivingTeamTest(){
        Assertions.assertFalse(testClassObject.isTradeGoodForReceivingTeam());

        ITeam goodTeam = mockObject.getTeamWithGoodPlayer();
        ITeam badTeam = mockObject.getTeamWithBadPlayer();
        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        offeringPlayers.add(goodTeam.getPlayers().get(0));
        ArrayList<IPlayer> receivingPlayers = new ArrayList<>();
        receivingPlayers.add(badTeam.getPlayers().get(0));

        ExchangingPlayerTradeOffer goodTradeForReceiver = new ExchangingPlayerTradeOffer(goodTeam,badTeam,offeringPlayers,receivingPlayers );
        Assertions.assertTrue(goodTradeForReceiver.isTradeGoodForReceivingTeam());
    }

    @Test
    public void getPlayerCombinedStrengthTest(){
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(mockObject.getStrongPlayer("player1"));
        players.add(mockObject.getStrongPlayer("player2"));

        Assertions.assertTrue(testClassObject.getPlayerCombinedStrength(players) == 50.0);
    }

    @Test
    public void getOfferingTeamTest(){
        Assertions.assertTrue(testClassObject.getOfferingTeam().getTeamName() == "TeamWithBadPlayer");
    }

    @Test
    public void getReceivingTeamTest(){
        Assertions.assertTrue(testClassObject.getReceivingTeam().getTeamName() == "TeamWithGoodPlayer");
    }
}
