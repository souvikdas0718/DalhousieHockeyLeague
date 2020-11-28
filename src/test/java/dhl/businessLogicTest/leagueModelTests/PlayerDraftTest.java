package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.PlayerDraft;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerDraft;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerDraftTest {

    IPlayerDraft testClassObject;
    ILeagueObjectModel league;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach
    public void initObject(){
        leagueFactory = LeagueModelAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();

        testClassObject = leagueFactory.createPlayerDraft(league);
    }

    @Test
    public void initDraftSequenceTest(){
        ((PlayerDraft)testClassObject).initDraftSequence(league);
        ArrayList<ArrayList<ITeam>> draftPickSequence = testClassObject.getDraftPickSequence();
        Assertions.assertTrue(draftPickSequence.size() > 0);
    }

    @Test
    public void swapDraftPickTest(){
        ArrayList<ArrayList<ITeam>> draftPickSequence = testClassObject.getDraftPickSequence();
        ITeam teamGivingDraftPick = draftPickSequence.get(1).get(0);
        ITeam teamGettingDraftPick = draftPickSequence.get(1).get(1);

        testClassObject.swapDraftPick(1,teamGettingDraftPick,teamGivingDraftPick);
        draftPickSequence = testClassObject.getDraftPickSequence();
        ITeam testTeam = draftPickSequence.get(1).get(0);
        Assertions.assertEquals(testTeam,teamGettingDraftPick);
    }
}
