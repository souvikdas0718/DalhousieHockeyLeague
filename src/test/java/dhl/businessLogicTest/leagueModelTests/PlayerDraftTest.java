package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.PlayerDraft;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class PlayerDraftTest {
/*
    IPlayerDraft testClassObject;
    ILeagueObjectModel league;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    ITeam[][] draftPickSequenceMock;
    @BeforeEach
    public void initObject(){
        leagueFactory = LeagueModelAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        initDraft(league);

        testClassObject = leagueFactory.createPlayerDraft();

    }

    // TODO: 28-11-2020 mock for this
    public void initDraft(ILeagueObjectModel league){
        int totalRound = 7;
        ArrayList<ITeam> list = new ArrayList<>();
        for(IConference c: league.getConferences()){
            for (IDivision d: c.getDivisions()){
                for (ITeam t: d.getTeams()){
                    list.add(t);
                }
            }
        }
        draftPickSequenceMock = new ITeam[list.size()][totalRound];
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < totalRound; j++ ){
                draftPickSequenceMock[i][j] = list.get(i);
            }
        }
    }

    @Test
    public void swapDraftPickTest(){

        ITeam teamGivingDraftPick = draftPickSequenceMock[0][1];
        ITeam teamGettingDraftPick = draftPickSequenceMock[1][1];

        testClassObject.swapDraftPick(1,teamGettingDraftPick,teamGivingDraftPick);
        draftPickSequenceMock = testClassObject.getDraftPickSequence();
        ITeam testTeam = draftPickSequenceMock[1][0];
        Assertions.assertEquals(testTeam,teamGettingDraftPick);
    }

*/
}
