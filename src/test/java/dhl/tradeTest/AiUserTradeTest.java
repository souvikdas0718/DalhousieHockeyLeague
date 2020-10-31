package dhl.tradeTest;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.AiUserTrade;
import dhl.trade.ExchangingPlayerTradeOffer;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.UserInputOutputForTrade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AiUserTradeTest {

    AiUserTrade testClassObject;
    TradeMock tradeMock;
    IUserInputOutput ioObjectMock;

    @BeforeEach
    public void initObject(){
        tradeMock = new TradeMock();
        ITeam offeringTeam = tradeMock.getTeamWithBadPlayer();
        ITeam recevingTeam = tradeMock.getTeamWithGoodPlayer();

        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        offeringPlayers.add(offeringTeam.getPlayers().get(0));

        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        playersWanted.add(recevingTeam.getPlayers().get(0));

        ITradeOffer tradeOffer = new ExchangingPlayerTradeOffer(offeringTeam,recevingTeam,offeringPlayers,playersWanted);
        ioObjectMock = new UserInputOutputForTrade();
        testClassObject = new AiUserTrade(tradeOffer , ioObjectMock);
    }

    @Test
    public void isTradeAcceptedTest(){

        ((UserInputOutputForTrade) ioObjectMock).setMockOutput("1");
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        ((UserInputOutputForTrade) ioObjectMock).setMockOutput("2");
        Assertions.assertFalse(testClassObject.isTradeAccepted());

    }

}
