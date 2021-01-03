package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PlayerDraftState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerDraftStateTest {
    SimulationStateAbstractFactory simulationFactory;
    StatesAbstractFactory statesFactory;
    StandingsAbstractFactory standingsFactory;

    IGameContext gameContext;
    SimulationContext simulationContext;
    PlayerDraftState playerDraftState;
    TeamMock teamMock;
    PlayerDraftAbstract playerDraft;
    StandingsAbstractFactory standingsAbstractFactory;
    LeagueMock leagueMock;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach()
    public void initObject() throws Exception {
        simulationFactory = SimulationStateAbstractFactory.instance();
        statesFactory = StatesAbstractFactory.instance();
        standingsFactory = StandingsAbstractFactory.instance();

        gameContext = statesFactory.createGameContext();
        simulationContext = statesFactory.createSimulationContext((GameContext)gameContext);
        leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();
        teamMock = leagueMockFactory.createTeamMock();

        simulationContext.setInMemoryLeague(leagueMock.getLeagueObjectModelFromJson());
        simulationContext.setUserTeam(teamMock.getTeamByName("Test Team"));
        List<IStandings> standings = new ArrayList<>();
        IStandings standing1 = standingsFactory.getStandings();
        standing1.setTeam(teamMock.getTeamByName("Winnipeg Privateers"));
        IStandings standing2 = standingsFactory.getStandings();
        standing2.setTeam(teamMock.getTeamByName("Halifax Astros"));
        standings.add(standing1);
        standings.add(standing2);
        simulationContext.setStandings(standings);
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        IStandingSystem standingSystem = standingsAbstractFactory.getStandingSystem();
        standingSystem.setStandingsList(standings);
        playerDraftState =  (PlayerDraftState) simulationFactory.getPlayerDraftState(simulationContext) ;
        LeagueModelAbstractFactory leagueModelAbstractFactory = LeagueModelAbstractFactory.instance();
        playerDraft = leagueModelAbstractFactory.createPlayerDraft();

        MockAbstractFactory mockFactory = MockAbstractFactory.instance();
        PlayerDraftMock playerDraftMock = mockFactory.getPlayerDraftMock();
        playerDraft.setDraftPickSequence(playerDraftMock.initializePlayerDraftPick());
    }

    @Test
    public void setDraftPickSequenceTest() {
        ITeam [][] draftPickSequence={{teamMock.getTeam()}};
        playerDraftState.setDraftPickSequence(draftPickSequence);
        Assertions.assertTrue(playerDraftState.getDraftPickSequence().length>0);
    }

    @Test
    public void seasonStateProcessTest(){
        playerDraftState.seasonStateProcess();
        ITeam[][] playerDraftPick = playerDraft.getDraftPickSequence();
        Assertions.assertEquals("Seattle Farmers",playerDraftPick[0][0].getTeamName());
    }

    @Test
    public void seasonStateExitProcessTest() throws Exception {
        PlayerDraftMock playerDraftMock = new PlayerDraftMock();
        playerDraftState.setDraftPickSequence(playerDraftMock.initializePlayerDraftPick());
        playerDraftState.setLeagueObjectModel(leagueMock.getLeagueObjectModelFromJson());
        List<IPlayer> players = new ArrayList<>();
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        playerDraftState.seasonStateExitProcess();
        for(IConference conference:leagueObjectModel.getConferences()){
            for(IDivision division:conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    players = team.getPlayers();
                }
            }
        }
        Assertions.assertTrue(players.size()==30);
    }

    @Test
    public void isValuePresentTest(){
       Assertions.assertFalse(playerDraftState.isValuePresent(null));
    }




}
