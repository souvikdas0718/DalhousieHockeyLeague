package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.TrainingState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class TrainingStateTest {

    SimulationContext simulationContext;
    TrainingState trainingState;
    GameContext gameState;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    IGameConfig gameConfig;
    GameConfigMock gameConfigMock;

//    LeagueObjectModelMocks mockLeagueObjectModel;
//    GameConfigMock gameConfigMock;
//    GameConfig gameConfig;
//    Training trainingParameterized;
//    JsonFilePathMock filePathMock;
//    ImportJsonFile importJsonFile;

    @BeforeEach
    public void initObject() throws Exception {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        trainingState = new TrainingState(simulationContext);
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        gameConfigMock = new GameConfigMock();
        gameConfig = gameConfigMock.getGameConfigMock();

//        mockLeagueObjectModel = new LeagueObjectModelMocks();
//        gameConfigMock = new GameConfigMock();
//        IInjury injurySystem = new Injury();
//        filePathMock = new JsonFilePathMock();
//        importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
//        gameConfig = new GameConfig(importJsonFile.getJsonObject());
//        trainingParameterized = new Training(injurySystem, gameConfig);
    }

    @Test
    public void TrainingStateTest() {
        trainingState = new TrainingState(simulationContext);
        Assertions.assertNotNull(trainingState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        trainingState = new TrainingState(simulationContext);
        Assertions.assertNotNull(trainingState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        trainingState = new TrainingState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2022);
        trainingState.setSimulationContext(simulationContext);
        Assertions.assertTrue(trainingState.getSimulationContext().getYear() == 2022);
    }

    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {

        simulationContext.setDaysSinceLastTraining(10);
        simulationContext.setGameConfig(gameConfig);
        System.out.println(gameConfig.getTrading());
        System.out.println(gameConfig.getDaysUntilStatIncreaseCheck());
//        System.out.println(Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getDaysUntilStatIncreaseCheck())));

//        simulationContext.setDaysSinceLastTraining(simulationContext.getDaysSinceLastTraining() + 1);
//        IGameConfig gameConfig = simulationContext.getGameConfig();
//        int daysUntilStatIncreaseCheck = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getDaysUntilStatIncreaseCheck()));
//        try {
//            if (daysUntilStatIncreaseCheck == simulationContext.getDaysSinceLastTraining()) {
//                ITraining training = new Training(simulationContext.getInjurySystem(), simulationContext.getGameConfig());
//                training.updatePlayerStats(simulationContext.getInMemoryLeague());
//                simulationContext.setDaysSinceLastTraining(0);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

        trainingState = new TrainingState(simulationContext);
        trainingState.seasonStateExitProcess();
        Assertions.assertTrue(trainingState.getSimulationContext().getCurrentSimulation() == trainingState.getSimulationContext().getSimulateGame());

        currentDate = playOffStartDate.plusDays(2);
        numberOfDays = DAYS.between(startOfSimulation, currentDate);
        simulationContext.setNumberOfDays((int) numberOfDays);
        trainingState = new TrainingState(simulationContext);
        trainingState.seasonStateExitProcess();
        Assertions.assertTrue(trainingState.getSimulationContext().getCurrentSimulation() == trainingState.getSimulationContext().getAging());

        LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
        LocalDate tradeDeadline = localDate.with(lastDayOfMonth()).with(previousOrSame(DayOfWeek.MONDAY));
        numberOfDays = DAYS.between(startOfSimulation, tradeDeadline) - 1;
        simulationContext.setNumberOfDays((int) numberOfDays);
        trainingState = new TrainingState(simulationContext);
        trainingState.seasonStateExitProcess();
        Assertions.assertTrue(trainingState.getSimulationContext().getCurrentSimulation() == trainingState.getSimulationContext().getSimulateGame());
    }
}
