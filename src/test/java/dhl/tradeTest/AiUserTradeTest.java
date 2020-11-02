package dhl.tradeTest;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.MockUserInputOutput;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.IUpdateUserTeamRoster;
import dhl.simulationStateMachine.UpdateUserTeamRoster;
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
    LeagueObjectModelMocks leagueObjectModelMocks;
    LeagueObjectModel leagueObjectModel;

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
        ioObjectMock = new MockUserInputOutput();
        IUpdateUserTeamRoster updateUserTeamRoster = new UpdateUserTeamRoster(ioObjectMock);
        testClassObject = new AiUserTrade(tradeOffer, ioObjectMock, updateUserTeamRoster);
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        leagueObjectModel= (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
    }

    @Test
    public void validateTeamRosterAfterTrade() throws Exception {
        leagueObjectModel.freeAgents = tradeMock.get50FreeAgents();
        ITeam team = tradeMock.getTeamWithGoodPlayer();

        ((MockUserInputOutput)ioObjectMock).setMockOutput("1");
        testClassObject.validateTeamRosterAfterTrade(team, leagueObjectModel);
        Assertions.assertTrue(team.checkIfSkatersGoaliesValid());

        team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer1"));
        team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer2"));
        IPlayer player = new Player("player1", "goalie", false,
                new PlayerStatistics(25,10,10,10,10));
        team.getPlayers().add(player);
        player = new Player("player2", "goalie", false,
                new PlayerStatistics(25,3,1,4,5));
        team.getPlayers().add(player);
        ((MockUserInputOutput)ioObjectMock).setMockOutput("0");
        testClassObject.validateTeamRosterAfterTrade(team, leagueObjectModel);
        Assertions.assertTrue(team.checkIfSkatersGoaliesValid());
    }

    @Test
    public void isTradeAcceptedTest() throws Exception {

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        ((MockUserInputOutput) ioObjectMock).setMockOutput("2");
        Assertions.assertFalse(testClassObject.isTradeAccepted());

        ((MockUserInputOutput) ioObjectMock).setMockOutput("3");
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            Assertions.assertFalse(testClassObject.isTradeAccepted());
        });
        Assertions.assertTrue(error.getMessage().contains("Wrong Input please give valid input"));

        ((MockUserInputOutput) ioObjectMock).setMockOutput("sdasd");
        Exception error2=Assertions.assertThrows(Exception.class,() ->{
            Assertions.assertFalse(testClassObject.isTradeAccepted());
        });
        Assertions.assertTrue(error.getMessage().contains("Wrong Input please give valid input"));

    }

}
