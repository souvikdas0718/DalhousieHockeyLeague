package dhl.businessLogicTest.simulationStateMachineTest;


import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.RegularSeasonStandingListMocks;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.PlayerDraftState;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.trade.TradeEngineAbstract;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimulationContextTest {

    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    RegularSeasonStandingListMocks regularSeasonStanding;
    ICoach coach;
    List<IPlayer> players;
    String manager;
    MockAbstractFactory mockAbstractFactory;
    TradeMockAbstractFactory tradeMockFactory;
    GameConfigMockForTrading gameConfigMock;
    StandingsAbstractFactory standingsAbstractFactory;
    IStandingSystem standingSystem;
    ContextAbstractFactory contextAbstractFactory;
    SimulationContext simulationContext;
    ILeagueObjectModel league;
    IScheduler scheduler;
    PlayerDraftState playerDraftState;
    SimulationStateAbstractFactory simulationFactory;
    AgingAbstractFactory agingFactory;
    Injury injury;
    ILeagueObjectModel leagueMock;
    LeagueModelMockAbstractFactory leagueMockFactory;
    IGameConfig iGameConfig;
    ITeam goodTeamMock;
    ITeam badTeamMock;
    ITeam userTeam;
    IUserInputOutput ioObject;
    TradeEngineAbstract tradeEngine;

    @BeforeEach
    public void initObject() {
        simulationFactory = SimulationStateAbstractFactory.instance();
        mockAbstractFactory = MockAbstractFactory.instance();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        mockLeagueObjectModel = mockAbstractFactory.getLeagueObjectModelMock();
        regularSeasonStanding = mockAbstractFactory.getRegularSeasonStandingListMock();
        coach = mockLeagueObjectModel.getSingleCoach();
        players = mockLeagueObjectModel.get20FreeAgentArrayMock();
        manager = "Harry";
        model20TeamMocks = mockAbstractFactory.getLeagueObjectModel20TeamMock();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        standingSystem = standingsAbstractFactory.getStandingSystem();
        contextAbstractFactory = ContextAbstractFactory.instance();
        simulationContext = contextAbstractFactory.createSimulationContext();
        league = mockLeagueObjectModel.getLeagueObjectMock();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        playerDraftState = (PlayerDraftState) simulationFactory.getPlayerDraftState(simulationContext);
        LeagueModelAbstractFactory leagueFactory = LeagueModelAbstractFactory.instance();
        PlayerDraftAbstract playerDraft = leagueFactory.createPlayerDraft();
        playerDraft.setDraftPickSequence(playerDraftState.getDraftPickSequence());
        agingFactory = AgingAbstractFactory.instance();
        injury = (Injury) agingFactory.createInjury();
        tradeMockFactory = TradeMockAbstractFactory.instance();
        gameConfigMock = tradeMockFactory.createGameConfigMockForTrading();
        goodTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        badTeamMock = tradeMockFactory.createTeamMockForTrade().getTeamWithBadPlayer();
        iGameConfig = gameConfigMock.getGameConfigMock();
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
        tradeEngine = (TradeEngineAbstract) TradeEngineAbstract.instance(iGameConfig, leagueMock, userTeam);
    }

    @Test
    public void getSeasonStartDateTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setSeasonStartDate(startOfSimulation);
        Assertions.assertEquals(simulationContext.getSeasonStartDate(), LocalDate.of(2020, 10, 1));
    }

    @Test
    public void setSeasonStartDateTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setSeasonStartDate(startOfSimulation);
        Assertions.assertEquals(simulationContext.getSeasonStartDate(), LocalDate.of(2020, 10, 1));
    }

    @Test
    public void getSeasonEndDateTest() {
        LocalDate seasonEndDate = LocalDate.of(2021, 04, 03);
        simulationContext.setSeasonEndDate(seasonEndDate);
        Assertions.assertEquals(simulationContext.getSeasonEndDate(), LocalDate.of(2021, 04, 03));
    }

    @Test
    public void setSeasonEndDateTest() {
        LocalDate seasonEndDate = LocalDate.of(2021, 04, 03);
        simulationContext.setSeasonEndDate(seasonEndDate);
        Assertions.assertEquals(simulationContext.getSeasonEndDate(), LocalDate.of(2021, 04, 03));
    }

    @Test
    public void getPlayOffStartDateTest() {
        LocalDate playOffStartDate = LocalDate.of(2021, 04, 14);
        simulationContext.setPlayOffStartDate(playOffStartDate);
        Assertions.assertEquals(simulationContext.getPlayOffStartDate(), LocalDate.of(2021, 04, 14));
    }

    @Test
    public void setPlayOffStartDateTest() {
        LocalDate playOffStartDate = LocalDate.of(2021, 04, 14);
        simulationContext.setPlayOffStartDate(playOffStartDate);
        Assertions.assertEquals(simulationContext.getPlayOffStartDate(), LocalDate.of(2021, 04, 14));
    }

    @Test
    public void getFinalDayTest() {
        LocalDate finalDay = LocalDate.of(2021, 04, 28);
        simulationContext.setFinalDay(finalDay);
        Assertions.assertEquals(simulationContext.getFinalDay(), LocalDate.of(2021, 04, 28));
    }

    @Test
    public void setFinalDayTest() {
        LocalDate finalDay = LocalDate.of(2021, 04, 28);
        simulationContext.setFinalDay(finalDay);
        Assertions.assertEquals(simulationContext.getFinalDay(), LocalDate.of(2021, 04, 28));
    }

    @Test
    public void getYearTest() {
        long year = LocalDate.now().getYear();
        simulationContext.setYear((int) year);
        Assertions.assertEquals(simulationContext.getYear(), 2020);
    }

    @Test
    public void setYearTest() {
        long year = LocalDate.now().getYear();
        simulationContext.setYear((int) year);
        Assertions.assertEquals(simulationContext.getYear(), 2020);
    }

    @Test
    public void getNumberOfDaysTest() {
        simulationContext.setNumberOfDays(1);
        Assertions.assertEquals(simulationContext.getNumberOfDays(), 1);
    }

    @Test
    public void setNumberOfDaysTest() {
        simulationContext.setNumberOfDays(1);
        Assertions.assertEquals(simulationContext.getNumberOfDays(), 1);
    }

    @Test
    public void getStartOfSimulationTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setStartOfSimulation(startOfSimulation);
        Assertions.assertEquals(simulationContext.getStartOfSimulation(), LocalDate.of(2020, 10, 1));
    }

    @Test
    public void setStartOfSimulationTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 10, 1);
        simulationContext.setStartOfSimulation(startOfSimulation);
        Assertions.assertEquals(simulationContext.getStartOfSimulation(), LocalDate.of(2020, 10, 1));
    }

    @Test
    public void getDaysSinceLastTrainingTest() {
        simulationContext.setDaysSinceLastTraining(1);
        Assertions.assertEquals(simulationContext.getDaysSinceLastTraining(), 1);
    }

    @Test
    public void setDaysSinceLastTrainingTest() {
        simulationContext.setDaysSinceLastTraining(1);
        Assertions.assertEquals(simulationContext.getDaysSinceLastTraining(), 1);
    }

    @Test
    public void getInMemoryLeagueTest() {
        simulationContext.setInMemoryLeague(league);
        Assertions.assertNotNull(simulationContext.getInMemoryLeague());
    }

    @Test
    public void setInMemoryLeagueTest() {
        simulationContext.setInMemoryLeague(league);
        Assertions.assertNotNull(simulationContext.getInMemoryLeague());
    }

    @Test
    public void getEndOfSimulationTest() {
        LocalDate endOfSimulation = LocalDate.of(2021, 04, 03);
        simulationContext.setEndOfSimulation(endOfSimulation);
        Assertions.assertEquals(simulationContext.getEndOfSimulation(), LocalDate.of(2021, 04, 03));
    }

    @Test
    public void setEndOfSimulationTest() {
        LocalDate endOfSimulation = LocalDate.of(2021, 04, 03);
        simulationContext.setEndOfSimulation(endOfSimulation);
        Assertions.assertEquals(simulationContext.getEndOfSimulation(), LocalDate.of(2021, 04, 03));
    }

    @Test
    public void getFinalScheduleTest() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setFinalSchedule(scheduler);
        Assertions.assertNotNull(simulationContext.getFinalSchedule());
    }

    @Test
    public void setFinalScheduleTest() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setFinalSchedule(scheduler);
        Assertions.assertNotNull(simulationContext.getFinalSchedule());
    }

    @Test
    public void getStandingsTest() {
        simulationContext.setStandings(model20TeamMocks.getGeneralStandings());
        Assertions.assertNotNull(simulationContext.getStandings());
    }

    @Test
    public void setStandingsTest() {
        simulationContext.setStandings(model20TeamMocks.getGeneralStandings());
        Assertions.assertNotNull(simulationContext.getStandings());
    }

    @Test
    public void getAdvanceToNextSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        Assertions.assertNotNull(simulationContext.getCurrentSimulation());
    }

    @Test
    public void getRegularSchedulerTest() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setRegularScheduler(scheduler);
        Assertions.assertNotNull(simulationContext.getRegularScheduler());
    }

    @Test
    public void setRegularScheduler() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setRegularScheduler(scheduler);
        Assertions.assertNotNull(simulationContext.getRegularScheduler());
    }

    @Test
    public void getPlayOffScheduleRound1() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setPlayOffScheduleRound1(scheduler);
        Assertions.assertNotNull(simulationContext.getPlayOffScheduleRound1());
    }

    @Test
    public void setPlayOffScheduleRound1() {
        scheduler.setFullSeasonSchedule(scheduler.getPlayOffScheduleRound1());
        simulationContext.setPlayOffScheduleRound1(scheduler);
        Assertions.assertNotNull(simulationContext.getPlayOffScheduleRound1());
    }


    @Test
    public void isSeasonInProgressTest() {
        simulationContext.setSeasonInProgress(true);
        Assertions.assertTrue(simulationContext.isSeasonInProgress());
    }

    @Test
    public void setSeasonInProgressTest() {
        simulationContext.setSeasonInProgress(true);
        Assertions.assertTrue(simulationContext.isSeasonInProgress());
    }

    @Test
    public void getPlayerDraftTest() {
        simulationContext.setPlayerDraft(playerDraftState);
        Assertions.assertNotNull(simulationContext.getPlayerDraft());
    }

    @Test
    public void setPlayerDraftTest() {
        simulationContext.setPlayerDraft(playerDraftState);
        Assertions.assertNotNull(simulationContext.getPlayerDraft());
    }


    @Test
    public void getTeamsPlayingInGameTest() {
        List<ITeam> team = new ArrayList<>();
        team.add(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        team.add(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        simulationContext.setTeamsPlayingInGame(team);
        Assertions.assertNotNull(simulationContext.getTeamsPlayingInGame());
    }

    @Test
    public void setTeamsPlayingInGameTest() {
        List<ITeam> team = new ArrayList<>();
        team.add(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        team.add(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        simulationContext.setTeamsPlayingInGame(team);
        Assertions.assertNotNull(simulationContext.getTeamsPlayingInGame());
    }


    @Test
    public void getInjurySystemTest() {
        simulationContext.setInjurySystem(injury);
        Assertions.assertNotNull(simulationContext.getInjurySystem());
    }

    @Test
    public void setInjurySystemTest() {
        simulationContext.setInjurySystem(injury);
        Assertions.assertNotNull(simulationContext.getInjurySystem());
    }

    @Test
    public void getTradeEngineTest() {
        simulationContext.setTradeEngine(tradeEngine);
        Assertions.assertNotNull(simulationContext.getTradeEngine());
    }

    @Test
    public void setTradeEngineTest() {
        simulationContext.setTradeEngine(tradeEngine);
        Assertions.assertNotNull(simulationContext.getTradeEngine());
    }

    @Test
    public void getIoObjectTest() {
        simulationContext.setIoObject(ioObject);
        Assertions.assertNotNull(simulationContext.getIoObject());
    }

    @Test
    public void setIoObjectTest() {
        simulationContext.setIoObject(ioObject);
        Assertions.assertNotNull(simulationContext.getIoObject());
    }

    @Test
    public void getUserTeamTest() {
        simulationContext.setUserTeam(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        Assertions.assertNotNull(simulationContext.getUserTeam());
    }

    @Test
    public void setUserTeamTest() {
        simulationContext.setUserTeam(scheduler.getPlayOffScheduleRound1().get(0).getTeamOne());
        Assertions.assertNotNull(simulationContext.getUserTeam());
    }

    @Test
    public void getGameConfigTest() {
        simulationContext.setGameConfig(iGameConfig);
        Assertions.assertNotNull(simulationContext.getGameConfig());
    }

    @Test
    public void setGameConfigTest() {
        simulationContext.setGameConfig(iGameConfig);
        Assertions.assertNotNull(simulationContext.getGameConfig());
    }

    @Test
    public void setSeasonSimulationState() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        Assertions.assertNotNull(simulationContext.getCurrentSimulation());
    }

    @Test
    public void getCurrentSimulationTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        Assertions.assertNotNull(simulationContext.getCurrentSimulation());
    }

    @Test
    public void setCurrentSimulationTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        Assertions.assertNotNull(simulationContext.getCurrentSimulation());
    }

    @Test
    public void getAdvanceTimeTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        Assertions.assertNotNull(simulationContext.getCurrentSimulation());
    }

    @Test
    public void setAdvanceTimeTest() {
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
        simulationContext.setAdvanceToNextSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getAdvanceToNextSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getAgingTest() {
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
        simulationContext.setAging(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getAging() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setAgingTest() {
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
        simulationContext.setAging(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getAging() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getExecuteTradesTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAging());
        simulationContext.setExecuteTrades(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getExecuteTrades() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setExecuteTradesTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAging());
        simulationContext.setExecuteTrades(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getExecuteTrades() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getPlayoffScheduleTest() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
        simulationContext.setPlayoffSchedule(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPlayoffSchedule() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setPlayoffScheduleTest() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
        simulationContext.setPlayoffSchedule(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPlayoffSchedule() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getInitializeSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        simulationContext.setInitializeSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getInitializeSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setInitializeSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        simulationContext.setInitializeSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getInitializeSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getInjuryCheckTest() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
        simulationContext.setInjuryCheck(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getInjuryCheck() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setInjuryCheckTest() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
        simulationContext.setInjuryCheck(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getInjuryCheck() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getPersistsSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getInitializeSeason());
        simulationContext.setPersistsSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPersistsSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setPersistsSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getInitializeSeason());
        simulationContext.setPersistsSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPersistsSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getPersistsSameSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        simulationContext.setPersistsSameSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPersistsSameSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setPersistsSameSeasonTest() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        simulationContext.setPersistsSameSeason(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getPersistsSameSeason() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getSimulateGameTest() {
        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());
        simulationContext.setSimulateGame(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getSimulateGame() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setSimulateGameTest() {
        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());
        simulationContext.setSimulateGame(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getSimulateGame() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void getTrainingTest() {
        simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        simulationContext.setTraining(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getTraining() == simulationContext.getCurrentSimulation());
    }

    @Test
    public void setTrainingTest() {
        simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        simulationContext.setTraining(simulationContext.getCurrentSimulation());
        Assertions.assertTrue(simulationContext.getTraining() == simulationContext.getCurrentSimulation());
    }


}
