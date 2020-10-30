package dhl.tradeTest;

import dhl.Mocks.GameConfigMock;
import dhl.importJson.Interface.IGameConfig;
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
        ourGameConfig.setRequiredObjectFromConfig("trading");

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
        // TODO: 30-10-2020 uncomment when random issue fix
        //Assertions.assertFalse(testClassObject.isTradeAccepted());

        ITradeOffer acceptedTrade = new ExchangingPlayerTradeOffer(strongTeam ,weakTeam,playersWanted,playersOffered);
        testClassObject = new AiAiTrade(acceptedTrade , ourGameConfig);

        Assertions.assertTrue(testClassObject.isTradeAccepted());
    }
}
