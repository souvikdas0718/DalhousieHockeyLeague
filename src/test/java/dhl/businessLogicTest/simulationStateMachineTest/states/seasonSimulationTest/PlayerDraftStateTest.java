package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PlayerDraftState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
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

    @BeforeEach()
    public void initObject() throws Exception {
        simulationFactory = SimulationStateAbstractFactory.instance();
        statesFactory = StatesAbstractFactory.instance();
        standingsFactory = StandingsAbstractFactory.instance();

        gameContext = statesFactory.createGameContext();
        simulationContext = statesFactory.createSimulationContext((GameContext)gameContext);
        LeagueModelMockAbstractFactory leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        LeagueMock leagueMock =  leagueMockFactory.createLeagueMock();
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
        playerDraftState =  (PlayerDraftState) simulationFactory.getPlayerDraftState(simulationContext) ;
        LeagueModelAbstractFactory leagueFactory = LeagueModelAbstractFactory.instance();
        IPlayerDraft playerDraft = leagueFactory.createPlayerDraft();
        playerDraft.setDraftPickSequence(playerDraftState.getDraftPickSequence());
    }

    @Test
    public void setDraftPickSequenceTest() {
        Assertions.assertTrue(playerDraftState.getDraftPickSequence().length>0);
    }

    @Test
    public void seasonStateProcessTest(){
       playerDraftState.seasonStateProcess();
       ITeam[][] playerDraftPick = playerDraftState.getDraftPickSequence();
       Assertions.assertEquals("Halifax Astros",playerDraftPick[0][0].getTeamName());
    }

    @Test
    public void getTeamTest(){
        playerDraftState.getTeams();
        Assertions.assertTrue(playerDraftState.getTeamsInLeague().size()>0);
    }

    @Test
    public void addDraftPlayersToTeamTest(){
        List<IPlayer> players = new ArrayList<>();
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        playerDraftState.addDraftPlayersToTeam();
        for(IConference conference:leagueObjectModel.getConferences()){
            for(IDivision division:conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    players = team.getPlayers();
                }
            }
        }
        Assertions.assertTrue(players.size()>30);
    }

    @Test
    public void seasonStateExitProcessTest(){
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
}
