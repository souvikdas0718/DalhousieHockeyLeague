package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.ExecuteTradesState;
import dhl.businessLogic.trade.TradingEngine;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.businessLogicTest.tradeTest.TradeMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExecuteTradesStateTest {

    SimulationContext simulationContext;
    ExecuteTradesState executeTradesState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    ILeagueObjectModel leagueMock;
    GameConfigMock gameConfig;
    IGameConfig iGameConfig;
    IScheduler scheduler;
    ITeam goodTeamMock;
    ITeam badTeamMock;
    ITeam userTeam;
    TradeMock tradeMock;
    ITradingEngine tradeEngine;


    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        gameConfig = new GameConfigMock();
        iGameConfig = gameConfig.getGameConfigMock();
        tradeMock = new TradeMock();
        goodTeamMock = tradeMock.getTeamWithGoodPlayer();
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        userTeam = new Team();
        leagueMock = mockLeagueObjectModel.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        leagueMock.setGameConfig(iGameConfig);
        tradeEngine = new TradingEngine(iGameConfig, leagueMock, userTeam);
        simulationContext = new SimulationContext(gameState);
    }

    @Test
    public void getSimulationContextTest() {
        executeTradesState = new ExecuteTradesState(simulationContext);
        Assertions.assertNotNull(executeTradesState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        executeTradesState = new ExecuteTradesState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2014);
        executeTradesState.setSimulationContext(simulationContext);
        Assertions.assertTrue(executeTradesState.getSimulationContext().getYear() == 2014);
    }

    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        badTeamMock.setRoster();
        simulationContext.setGameConfig(iGameConfig);
        simulationContext.setInMemoryLeague(leagueMock);
        simulationContext.setTradeEngine(tradeEngine);

        executeTradesState = new ExecuteTradesState(simulationContext);
        executeTradesState.seasonStateProcess();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }

    @Test
    public void seasonStateExitProcessTest() {
        executeTradesState = new ExecuteTradesState(simulationContext);
        executeTradesState.seasonStateExitProcess();
        Assertions.assertTrue(executeTradesState.getSimulationContext().getCurrentSimulation() == executeTradesState.getSimulationContext().getAging());
    }
}
