package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.MockUserInputOutput;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.simulationStateMachine.UpdateUserTeamRoster;
import dhl.businessLogic.trade.AiUserTrade;
import dhl.businessLogic.trade.ExchangingPlayerTradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
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
    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach
    public void initObject() {
        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMock = new TradeMock();
        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        ioObjectMock = new MockUserInputOutput();
        leagueObjectModelMocks = new LeagueObjectModelMocks();

        ITeam offeringTeam = tradeMock.getTeamWithBadPlayer();
        ITeam recevingTeam = tradeMock.getTeamWithGoodPlayer();

        offeringPlayers.add(offeringTeam.getPlayers().get(0));
        playersWanted.add(recevingTeam.getPlayers().get(0));
        ITradeOffer tradeOffer = tradeFactory.createExchangingPlayerTradeOffer(offeringTeam, recevingTeam, offeringPlayers, playersWanted);
        IUpdateUserTeamRoster updateUserTeamRoster = new UpdateUserTeamRoster(ioObjectMock);
        testClassObject = (AiUserTrade) tradeFactory.createAiUserTrade(tradeOffer, ioObjectMock, updateUserTeamRoster);
        leagueObjectModel = (LeagueObjectModel) leagueObjectModelMocks.getLeagueObjectMock();
    }

    @Test
    public void validateTeamRosterAfterTrade() throws Exception {

        leagueObjectModel.freeAgents = tradeMock.get50FreeAgents();
        ITeam team = tradeMock.getTeamWithGoodPlayer();

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");

        testClassObject.validateTeamRosterAfterTrade(team, leagueObjectModel);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());

        team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer1"));
        team.getPlayers().add(tradeMock.getWeakPlayer("randomPlayer2"));

        IPlayer player = leagueFactory.createPlayer("player1", "goalie", false,
                leagueFactory.createPlayerStatistics(25, 10, 10, 10, 10));
        team.getPlayers().add(player);
        player = leagueFactory.createPlayer("player2", "goalie", false,
                leagueFactory.createPlayerStatistics(25, 3, 1, 4, 5));
        team.getPlayers().add(player);
        ((MockUserInputOutput) ioObjectMock).setMockOutput("0");
        testClassObject.validateTeamRosterAfterTrade(team, leagueObjectModel);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());
    }

    @Test
    public void isTradeAcceptedTest() throws Exception {

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");
        Assertions.assertTrue(testClassObject.isTradeAccepted());

        ((MockUserInputOutput) ioObjectMock).setMockOutput("2");
        Assertions.assertFalse(testClassObject.isTradeAccepted());

    }

}
