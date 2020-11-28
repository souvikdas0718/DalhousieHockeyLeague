package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PlayerDraftState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerDraftStateTest {
    SimulationStateAbstractFactory simulationFactory;
    StatesAbstractFactory statesFactory;

    IGameContext gameContext;
    SimulationContext simulationContext;
    PlayerDraftState playerDraftState;

    @BeforeEach()
    public void initObject() throws Exception {
        simulationFactory = SimulationStateAbstractFactory.instance();
        statesFactory = StatesAbstractFactory.instance();
        gameContext = statesFactory.createGameContext();
        simulationContext = statesFactory.createSimulationContext((GameContext)gameContext);
        LeagueModelMockAbstractFactory leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        LeagueMock leagueMock =  leagueMockFactory.createLeagueMock();
        TeamMock teamMock = leagueMockFactory.createTeamMock();
        simulationContext.setInMemoryLeague(leagueMock.getLeagueObjectModelFromJson());
        simulationContext.setUserTeam(teamMock.getTeamByName("Test Team"));
        simulationContext.setStandings(new ArrayList<>());
        playerDraftState =  (PlayerDraftState) simulationFactory.getPlayerDraftState(simulationContext) ;
    }

    @Test
    public void setDraftPickSequenceTest() {
        String [][] draftPickSequence={{"Team1","Team2"}};
        playerDraftState.setDraftPickSequence(draftPickSequence);
        Assertions.assertTrue(playerDraftState.getDraftPickSequence().length>0);
    }


    @Test
    public void seasonStateProcessTest(){
       playerDraftState.seasonStateProcess();
       String[][] playerDraftPick = playerDraftState.getDraftPickSequence();
       Assertions.assertEquals("Winnipeg Privateers",playerDraftPick[0][0]);
    }

    @Test
    public void getTeamTest(){
        playerDraftState.getTeams();
        Assertions.assertTrue(playerDraftState.getTeamsInLeague().size()>0);
    }
}
