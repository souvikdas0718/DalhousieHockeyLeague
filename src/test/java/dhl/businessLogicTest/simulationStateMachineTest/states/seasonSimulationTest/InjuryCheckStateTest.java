package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InjuryCheckState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SeasonSimulationStateFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

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
        injuryCheckState = (InjuryCheckState) seasonSimulationStateFactory.getInjuryCheckState(simulationContext);
        injuryCheckState.seasonStateProcess();
        Assertions.assertEquals(-1, injuryCheckState.getSimulationContext().getInMemoryLeague().getConferences().get(0).getDivisions().get(0).getTeams().get(0).getPlayers().get(0).getPlayerInjuredDays());

    }

    @Test
    public void seasonStateExitProcessTest() {
        injuryCheckState.seasonStateExitProcess();
    }
}
