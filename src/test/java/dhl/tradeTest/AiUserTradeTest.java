package dhl.tradeTest;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.States.CreateTeamStateUI;
import dhl.trade.AiUserTrade;
import dhl.trade.ExchangingPlayerTradeOffer;
import dhl.trade.Interface.ITradeOffer;
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
        ioObjectMock = new MockUserInputOutputForTrade();
        testClassObject = new AiUserTrade(tradeOffer , ioObjectMock);
    }

    @Test
    public void isTradeAcceptedTest() throws Exception {

        ((MockUserInputOutputForTrade) ioObjectMock).setMockOutput("1");
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        ((MockUserInputOutputForTrade) ioObjectMock).setMockOutput("2");
        Assertions.assertFalse(testClassObject.isTradeAccepted());

        ((MockUserInputOutputForTrade) ioObjectMock).setMockOutput("3");
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            Assertions.assertFalse(testClassObject.isTradeAccepted());
        });
        Assertions.assertTrue(error.getMessage().contains("Wrong Input please give valid input"));

        ((MockUserInputOutputForTrade) ioObjectMock).setMockOutput("sdasd");
        Exception error2=Assertions.assertThrows(Exception.class,() ->{
            Assertions.assertFalse(testClassObject.isTradeAccepted());
        });
        Assertions.assertTrue(error.getMessage().contains("Wrong Input please give valid input"));

    }

}
