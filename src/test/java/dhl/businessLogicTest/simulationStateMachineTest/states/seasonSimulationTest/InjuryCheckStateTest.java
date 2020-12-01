package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InjuryCheckState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class InjuryCheckStateTest {
    SimulationContext simulationContext;
    InjuryCheckState injuryCheckState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    ContextAbstractFactory contextAbstractFactory;
    MockAbstractFactory mockAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    IScheduler scheduler;
    IGameConfig gameConfig;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    ILeagueObjectModel leagueObjectModel;
    AgingAbstractFactory agingFactory;
    IInjury injury;
    IStandingSystem standingSystem;
    StandingsAbstractFactory standingsAbstractFactory;

    @BeforeEach
    public void initObject() throws Exception {
        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        injuryCheckState = (InjuryCheckState) seasonSimulationStateFactory.getInjuryCheckState(simulationContext);
        mockAbstractFactory = MockAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        agingFactory = AgingAbstractFactory.instance();
        injury = (Injury) agingFactory.createInjury();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        gameConfig = gameplayConfigMock.getAgingGameConfig();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
    }

    @Test
    public void InjuryCheckStateTest() {
        Assertions.assertNotNull(injuryCheckState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(injuryCheckState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2022);
        injuryCheckState.setSimulationContext(simulationContext);
        Assertions.assertTrue(injuryCheckState.getSimulationContext().getYear() == 2022);
    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setDaysSinceLastTraining(192);
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInjurySystem(injury);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        standingSystem.createStandings(leagueObjectModel);
        List<ITeam> teamList = new ArrayList<>();
        teamList.add(standingSystem.getStandingsList().get(0).getTeam());
        teamList.add(standingSystem.getStandingsList().get(1).getTeam());
        simulationContext.setTeamsPlayingInGame(teamList);
        injuryCheckState = (InjuryCheckState) seasonSimulationStateFactory.getInjuryCheckState(simulationContext);
        injuryCheckState.seasonStateProcess();
        Assertions.assertEquals(injuryCheckState.getSimulationContext().getTeamsPlayingInGame().size(), 0);

    }

    @Test
    public void seasonStateExitProcessTest() {
        injuryCheckState.seasonStateExitProcess();
    }
}
