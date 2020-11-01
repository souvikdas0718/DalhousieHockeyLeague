package dhl.tradeTest;

import dhl.Mocks.GameConfigMock;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.AiAiTrade;
import dhl.trade.ExchangingPlayerTradeOffer;
import dhl.trade.Interface.ITradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AiAiTradeTest {

    AiAiTrade testClassObject;
    TradeMock tradeMock;
    GameConfigMock gameConfigMock;
    IGameConfig ourGameConfig;
    ArrayList<IPlayer> playersOffered,playersWanted;
    ITeam strongTeam,weakTeam;
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
