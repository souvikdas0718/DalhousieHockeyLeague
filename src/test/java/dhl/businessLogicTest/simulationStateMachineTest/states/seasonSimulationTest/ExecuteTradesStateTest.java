package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.ExecuteTradesState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExecuteTradesStateTest {

    SimulationContext simulationContext;
    ExecuteTradesState executeTradesState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    ILeagueObjectModel leagueMock;
    GameConfigMockForTrading gameConfigMock;
    TradeMockAbstractFactory tradeMockFactory;
    IGameConfig iGameConfig;
    ITeam goodTeamMock;
    ITeam badTeamMock;
    ITeam userTeam;
    TradeMockAbstractFactory tradeMockAbstractFactory;
    ITradingEngine tradeEngine;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    MockAbstractFactory mockAbstractFactory;

    @BeforeEach
    public void initObject() {

        contextAbstractFactory = ContextAbstractFactory.instance();
        gameState = contextAbstractFactory.createGameContext();
        simulationContext = contextAbstractFactory.createSimulationContext();
        mockAbstractFactory = MockAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        iGameConfig = gameConfigMock.getGameConfigMock();
        tradeMockAbstractFactory = TradeMockAbstractFactory.instance();
        goodTeamMock = tradeMockAbstractFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        badTeamMock = tradeMockAbstractFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        userTeam = new Team();
        leagueMock = mockLeagueObjectModel.getLeagueObjectMock();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        leagueMock.setGameConfig(iGameConfig);
        tradeEngine = ITradingEngine.instance(iGameConfig, leagueMock, userTeam);
        seasonSimulationStateFactory = (SeasonSimulationStateFactory) SeasonSimulationStateFactory.instance();
        executeTradesState = (ExecuteTradesState) seasonSimulationStateFactory.getExecuteTradesState(simulationContext);
    }

    @Test
    public void getSimulationContextTest() {
        Assertions.assertNotNull(executeTradesState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulationContext.setYear(2014);
        executeTradesState.setSimulationContext(simulationContext);
        Assertions.assertTrue(executeTradesState.getSimulationContext().getYear() == 2014);
    }

    @Test
    public void seasonStateProcessTest() {
        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
        badTeamMock.setRoster();
        simulationContext.setGameConfig(iGameConfig);
        simulationContext.setInMemoryLeague(leagueMock);
        simulationContext.setTradeEngine(tradeEngine);
        executeTradesState = (ExecuteTradesState) seasonSimulationStateFactory.getExecuteTradesState(simulationContext);
        executeTradesState.seasonStateProcess();
        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());
    }

    @Test
    public void seasonStateExitProcessTest() {
        executeTradesState.seasonStateExitProcess();
        Assertions.assertTrue(executeTradesState.getSimulationContext().getCurrentSimulation() == executeTradesState.getSimulationContext().getAging());
    }
}
