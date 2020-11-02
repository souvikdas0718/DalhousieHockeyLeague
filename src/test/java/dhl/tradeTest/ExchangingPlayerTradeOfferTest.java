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
    public void initObject() {
        mockObject = new TradeMock();
        ITeam strongTeam = mockObject.getTeamWithGoodPlayer();
        ITeam weakTeam = mockObject.getTeamWithBadPlayer();
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        playersOffered.add(weakTeam.getPlayers().get(0));
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        playersWanted.add(strongTeam.getPlayers().get(0));
        testClassObject = new ExchangingPlayerTradeOffer(weakTeam, strongTeam, playersOffered, playersWanted);
    }

    @Test
    public void performTradeTest() {

        testClassObject.implementTrade();
        IPlayer playerNowInStrongTeam = testClassObject.playersOffered.get(0);
        IPlayer playerNowInWeakTeam = testClassObject.playersWantedInExchange.get(0);

        Assertions.assertTrue(testClassObject.getOfferingTeam().getPlayers().contains(playerNowInWeakTeam));
        Assertions.assertTrue(testClassObject.getReceivingTeam().getPlayers().contains(playerNowInStrongTeam));

    }

    @Test
    public void getOfferingTeamTest() {
        Assertions.assertTrue(testClassObject.getOfferingTeam().getTeamName() == "TeamWithBadPlayer");
    }

    @Test
    public void getReceivingTeamTest() {
        Assertions.assertTrue(testClassObject.getReceivingTeam().getTeamName() == "TeamWithGoodPlayer");
    }

    @Test
    public void getOfferingPlayersTest() {
        ArrayList<IPlayer> offeredPlayers = testClassObject.getOfferingPlayers();
        String OfferedPlayerName = offeredPlayers.get(0).getPlayerName();
        Assertions.assertTrue(OfferedPlayerName.equals("WeakPlayer1"));
    }

    @Test
    public void getPlayersWantedInReturn() {
        ArrayList<IPlayer> playersWanted = testClassObject.getPlayersWantedInReturn();
        String WantedPlayerName = playersWanted.get(0).getPlayerName();
        Assertions.assertTrue(WantedPlayerName.equals("Player1"));
    }
}
