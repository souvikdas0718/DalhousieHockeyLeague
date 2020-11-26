package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.Mocks.MockUserInputOutput;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.simulationStateMachine.UpdateUserTeamRoster;
import dhl.businessLogic.trade.AiUserTrade;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AiUserTradeTest {

    AiUserTrade testClassObject;
    IUserInputOutput ioObjectMock;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueObjectModel leagueObjectModel;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;

    @BeforeEach
    public void initObject() {
        ioObjectMock = new MockUserInputOutput();

        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();

        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

        ITeam offeringTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        ITeam recevingTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        offeringPlayers.add(offeringTeam.getPlayers().get(0));
        playersWanted.add(recevingTeam.getPlayers().get(0));
        ITradeOffer tradeOffer = tradeFactory.createExchangingPlayerTradeOffer(offeringTeam, recevingTeam, offeringPlayers, playersWanted);
        IUpdateUserTeamRoster updateUserTeamRoster = new UpdateUserTeamRoster(ioObjectMock);
        testClassObject = (AiUserTrade) tradeFactory.createAiUserTrade(tradeOffer, ioObjectMock, updateUserTeamRoster);
        leagueObjectModel = (LeagueObjectModel) leagueMockFactory.createLeagueMock().getLeagueObjectModel();
    }

    @Test
    public void validateTeamRosterAfterTrade() throws Exception {

        leagueObjectModel.freeAgents = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");

        testClassObject.validateTeamRosterAfterTrade(team, leagueObjectModel);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());

        team.getPlayers().add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("randomPlayer1", PlayerPosition.DEFENSE.toString()));
        team.getPlayers().add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("randomPlayer2", PlayerPosition.DEFENSE.toString()));

        IPlayer player = leagueFactory.createPlayer("player1", "goalie", false,
                leagueFactory.createPlayerStatistics(10, 10, 10, 10));
        team.getPlayers().add(player);
        player = leagueFactory.createPlayer("player2", "goalie", false,
                leagueFactory.createPlayerStatistics( 3, 1, 4, 5));
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
