package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.DraftPickTradeOffer;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.TradeOfferAbstract;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DraftPickTradeOfferTest {

    TradeOfferAbstract testClassObject;
    ITeam offeringTeam, receivingTeam;
    ITeam[][] draftPickSequenceMock;

    TradeAbstractFactory tradeFactory;
    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMock;

    @BeforeEach
    public void initObject(){
        tradeFactory = new TradeConcreteFactory();
        leagueFactory = LeagueModelAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMock = LeagueModelMockAbstractFactory.instance();

        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        TeamMock teamMock = leagueMock.createTeamMock();
        offeringTeam = teamMock.getTeamByName("Ontario");
        receivingTeam = teamMock.getTeamByName("Torronto");
        playersOffered.add(offeringTeam.getPlayers().get(0));
        playersWanted.add(receivingTeam.getPlayers().get(0));

        draftPickSequenceMock = new Team[2][2];
        draftPickSequenceMock[0][0] = offeringTeam;
        draftPickSequenceMock[0][1] = receivingTeam;
        draftPickSequenceMock[1][0] = offeringTeam;
        draftPickSequenceMock[1][1] = receivingTeam;

        PlayerDraftAbstract playerDraft = PlayerDraftAbstract.instance();
        playerDraft.setDraftPickSequence(draftPickSequenceMock);
        testClassObject = tradeFactory.createDraftPickTradeOffer(offeringTeam,receivingTeam, playersWanted,playerDraft);
    }

    @Test
    public void implementTradeTest(){
        testClassObject.implementTrade();
        Assertions.assertEquals(draftPickSequenceMock[0][0] , draftPickSequenceMock[0][1]);
    }

    @Test
    public void setRoundFromDraftTest(){
        int round = ((DraftPickTradeOffer)testClassObject).setRoundFromDraft(offeringTeam);
        Assertions.assertTrue(round == 0);
    }

    @Test
    public void isTradePossibleTest(){
        boolean testResult = ((DraftPickTradeOffer) testClassObject).isTradePossible();
        Assertions.assertFalse(testResult);

        ((DraftPickTradeOffer)testClassObject).setRoundFromDraft(offeringTeam);
        testResult = ((DraftPickTradeOffer) testClassObject).isTradePossible();
        Assertions.assertTrue(testResult);
    }
}
