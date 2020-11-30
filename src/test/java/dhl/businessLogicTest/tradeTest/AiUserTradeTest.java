package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.importJsonTest.mocks.MockUserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.AiUserTrade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class AiUserTradeTest {

    AiUserTrade testClassObject;
    IUserInputOutput ioObjectMock;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueObjectModel leagueObjectModel;
    IGameConfig ourGameConfig;

    ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
    ArrayList<IPlayer> playersWanted = new ArrayList<>();
    ITeam offeringTeam,recevingTeam;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;

    @BeforeEach
    public void initObject() {
        IUserInputOutput.setFactory(MockUserInputOutput.instance());
        ioObjectMock = IUserInputOutput.getInstance();

        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();

        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

        offeringTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        recevingTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        offeringPlayers.add(offeringTeam.getPlayers().get(0));
        playersWanted.add(recevingTeam.getPlayers().get(0));

        ourGameConfig = tradeMockFactory.createGameConfigMockForTrading().getGameConfigMock();
        leagueObjectModel = (LeagueObjectModel) leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject = (AiUserTrade) tradeFactory.createAiUserTrade(ioObjectMock, leagueObjectModel);
    }

    @Test
    public void validateTeamRosterAfterTrade(){

        leagueObjectModel.freeAgents = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");

        testClassObject.validateTeamRosterAfterTrade(team);
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
        testClassObject.validateTeamRosterAfterTrade(team);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());
    }

    @Test
    public void isTradeAcceptedTest() {

        ((MockUserInputOutput) ioObjectMock).setMockOutput("1");
        Assertions.assertTrue(testClassObject.isTradeAccepted(offeringPlayers, playersWanted,recevingTeam ));

        ((MockUserInputOutput) ioObjectMock).setMockOutput("2");
        Assertions.assertFalse(testClassObject.isTradeAccepted(offeringPlayers, playersWanted,recevingTeam));

        ((MockUserInputOutput) ioObjectMock).setMockOutput("3");
        Assertions.assertFalse(testClassObject.isTradeAccepted(offeringPlayers, playersWanted,recevingTeam));
    }
}
