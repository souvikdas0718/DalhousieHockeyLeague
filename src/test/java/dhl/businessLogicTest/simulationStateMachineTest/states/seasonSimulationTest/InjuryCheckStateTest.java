package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InjuryCheckState;
import dhl.businessLogic.traning.Training;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.inputOutput.importJson.ImportJsonFile;
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
    IScheduler scheduler;
    GameConfigMock gameConfigMock;
//    GameConfig gameConfig;
    IGameConfig gameConfig;
    Training trainingParameterized;
    JsonFilePathMock filePathMock;
    ImportJsonFile importJsonFile;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    ILeagueObjectModel leagueObjectModel;
    AgingAbstractFactory agingFactory;
    Injury injury;

    @BeforeEach
    public void initObject() throws Exception {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        injuryCheckState = new InjuryCheckState(simulationContext);
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        gameConfigMock = new GameConfigMock();
        IInjury injurySystem = new Injury();
        filePathMock = new JsonFilePathMock();
        importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized = new Training(injurySystem, gameConfig);
        agingFactory= AgingAbstractFactory.instance();
        injury = (Injury) agingFactory.createInjury();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        gameConfig = gameplayConfigMock.getAgingGameConfig();
    }

    @Test
    public void InjuryCheckStateTest() {
        injuryCheckState = new InjuryCheckState(simulationContext);
        Assertions.assertNotNull(injuryCheckState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        injuryCheckState = new InjuryCheckState(simulationContext);
        Assertions.assertNotNull(injuryCheckState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        injuryCheckState = new InjuryCheckState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2022);
        injuryCheckState.setSimulationContext(simulationContext);
        Assertions.assertTrue(injuryCheckState.getSimulationContext().getYear() == 2022);
    }


    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
        simulationContext.setDaysSinceLastTraining(192);
        simulationContext.setGameConfig(gameConfig);
        simulationContext.setInjurySystem(injury);
        simulationContext.setInMemoryLeague(leagueObjectModel);
        injuryCheckState = new InjuryCheckState(simulationContext);
        injuryCheckState.seasonStateProcess();
        Assertions.assertEquals(-1, injuryCheckState.getSimulationContext().getInMemoryLeague().getConferences().get(0).getDivisions().get(0).getTeams().get(0).getPlayers().get(0).getPlayerInjuredDays());

    }

    @Test
    public void seasonStateExitProcessTest() {
        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
        LocalDate seasonStartDate = LocalDate.of(2020, 10, 1);
        LocalDate regularSeasonEndDate = LocalDate.of(2021, 04, 3);
        LocalDate playOffStartDate = LocalDate.of(2021, 04, 10);
        LocalDate currentDate = LocalDate.now();
        long numberOfDays = DAYS.between(startOfSimulation, currentDate);

        scheduler.setSeasonStartDate(seasonStartDate);
        scheduler.setSeasonEndDate(regularSeasonEndDate);
        simulationContext.setPlayOffScheduleRound1(scheduler);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays((int) numberOfDays);
        simulationContext.setYear(2020);

        injuryCheckState = new InjuryCheckState(simulationContext);
        injuryCheckState.seasonStateExitProcess();
        Assertions.assertTrue(injuryCheckState.getSimulationContext().getCurrentSimulation() == injuryCheckState.getSimulationContext().getSimulateGame());


        currentDate = playOffStartDate.plusDays(2);
        numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setNumberOfDays((int) numberOfDays);
        injuryCheckState = new InjuryCheckState(simulationContext);
        injuryCheckState.seasonStateExitProcess();
        Assertions.assertTrue(injuryCheckState.getSimulationContext().getCurrentSimulation() == injuryCheckState.getSimulationContext().getAging());

        LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
        LocalDate tradeDeadline = localDate.with(lastDayOfMonth()).with(previousOrSame(DayOfWeek.MONDAY));
        numberOfDays = DAYS.between(startOfSimulation, tradeDeadline) - 1;
        simulationContext.setNumberOfDays((int) numberOfDays);
        injuryCheckState = new InjuryCheckState(simulationContext);
        injuryCheckState.seasonStateExitProcess();
        Assertions.assertTrue(injuryCheckState.getSimulationContext().getCurrentSimulation() == injuryCheckState.getSimulationContext().getSimulateGame());
    }
}
