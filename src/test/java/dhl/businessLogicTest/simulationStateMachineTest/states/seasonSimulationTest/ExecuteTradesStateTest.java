package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.ExecuteTradesState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogic.trade.TradeEngineAbstract;
import dhl.businessLogic.trade.TradingEngine;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    TradingEngine tradeEngine;
    ContextAbstractFactory contextAbstractFactory;
    SeasonSimulationStateFactory seasonSimulationStateFactory;
    MockAbstractFactory mockAbstractFactory;
    IUserInputOutput ioObject;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach
    public void initObject() {

        contextAbstractFactory = ContextAbstractFactory.instance();
        leagueFactory = LeagueModelAbstractFactory.instance();
        simulationContext = contextAbstractFactory.createSimulationContext();
        mockAbstractFactory = MockAbstractFactory.instance();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        iGameConfig = gameConfigMock.getGameConfigMock();
        goodTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        leagueMock.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        leagueMock.setGameConfig(iGameConfig);
        IGeneralManager manager = leagueFactory.createGeneralManager("Manager", "normal");
        ICoach coach = leagueFactory.createCoach("coach", 10, 10, 10, 10);
        List<IPlayer> playersList = leagueMock.getFreeAgents();
        userTeam = leagueFactory.createTeam("ABC", manager, coach, playersList);
        ioObject = IUserInputOutput.getInstance();
        ;
        tradeEngine = (TradingEngine) TradeEngineAbstract.instance(iGameConfig, leagueMock, userTeam);
        tradeEngine.setIoObject(ioObject);
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
