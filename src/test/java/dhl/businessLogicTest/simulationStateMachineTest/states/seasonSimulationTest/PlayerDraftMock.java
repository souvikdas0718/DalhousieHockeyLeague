package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;

import java.util.ArrayList;
import java.util.List;

public class PlayerDraftMock {
    ITeam [][] draftPickSequence = new Team[32][7];

    public ITeam [][]  initializePlayerDraftPick() throws Exception {
        List<ITeam> teamsInLeague = getTeams();
        if(teamsInLeague.size()>0){
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 7; j++) {
                    draftPickSequence[i][j]=teamsInLeague.get(i);
                }
            }
        }
        return draftPickSequence;
    }

    public List<ITeam>  getTeams() throws Exception {
        List<ITeam> teamsInLeague  = new ArrayList<>();
        LeagueModelMockAbstractFactory leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        LeagueMock leagueMock =  leagueMockFactory.createLeagueMock();

        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectModelFromJson();
        for(IConference conference:leagueObjectModel.getConferences()){
            for(IDivision division:conference.getDivisions()){
                for(ITeam team:division.getTeams()){
                    teamsInLeague.add(team);
                }
            }
        }
        return teamsInLeague;
    }
}
