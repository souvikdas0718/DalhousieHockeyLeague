package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerDraftTest {
    PlayerDraftAbstract playerDraft;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    ITeam[][] draftPickSequenceMock;
    ITeam team1;
    ITeam team2;

    @BeforeEach
    public void initObject(){
        leagueFactory = LeagueModelAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

        playerDraft = leagueFactory.createPlayerDraft();

        TeamMock teamMock = leagueMockFactory.createTeamMock();
        team1 = teamMock.getTeamByName("Ontario");
        team2 = teamMock.getTeamByName("Torronto");
        draftPickSequenceMock = new Team [2][2];
        draftPickSequenceMock[0][0] = team1;
        draftPickSequenceMock[0][1] = team2;
        draftPickSequenceMock[1][0] = team1;
        draftPickSequenceMock[1][1] = team2;

    }

    @Test
    public void swapDraftPickTest(){
        playerDraft.setDraftPickSequence(draftPickSequenceMock);
        ITeam teamGettingDraft = team1;
        ITeam teamGivingDraft = team2;
        playerDraft.swapDraftPick(1, teamGettingDraft, teamGivingDraft);
        ITeam testTeam = draftPickSequenceMock[1][0];
        Assertions.assertEquals(testTeam,teamGettingDraft);
    }

    @Test
    public void setDraftPickSequenceTest() {
        playerDraft.setDraftPickSequence(draftPickSequenceMock);
        Assertions.assertTrue(playerDraft.getDraftPickSequence().length>0);
    }

}
