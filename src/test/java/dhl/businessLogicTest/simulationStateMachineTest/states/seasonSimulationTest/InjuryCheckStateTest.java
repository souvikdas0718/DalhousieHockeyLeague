package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.IStandings;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.InjuryCheckState;
import dhl.businessLogic.traning.Training;
import dhl.inputOutput.importJson.GameConfig;
import dhl.inputOutput.importJson.ImportJsonFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class InjuryCheckStateTest {
    SimulationContext simulationContext;
    InjuryCheckState injuryCheckState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    IScheduler scheduler;
    GameConfigMock gameConfigMock;
    GameConfig gameConfig;
    Training trainingParameterized;
    JsonFilePathMock filePathMock;
    ImportJsonFile importJsonFile;

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
    public void seasonStateProcessTest() throws Exception {
//        ILeagueObjectModelDB mockLeagueObject = new MockDatabase();
//        ILeagueObjectModel newLeagueObject = trainingParameterized.updatePlayerStats(mockLeagueObject.loadLeagueModel("Dhl", "Mock Team2"));
//        simulationContext.setInMemoryLeague(newLeagueObject);
//        simulationContext.setTeamsPlayingInGame(mockLeagueObjectModel.getTeamArrayMock());
//        simulationContext.setGameConfig(gameConfigMock.getGameConfigMock());
////        simulationContext.getTeamsPlayingInGame().get(0).getPlayers().get(0).getPlayerStats();
////        for (ITeam team :simulationContext.getTeamsPlayingInGame()) {
////            if(team.getTeamName() == simulationContext.getTeamsPlayingInGame().get(0).getTeamName()) {
////                team.getPlayers().get(0).setPlayerStats(newLeagueObject.);
////            }
////        }
////        simulationContext.getTeamsPlayingInGame().get(0).getPlayers().get(0).getPlayerStats();
////        simulationContext.setTeamsPlayingInGame(simulationContext.getTeamsPlayingInGame().get(0).getPlayers().get(0).getPlayerStats());
//        System.out.println(simulationContext.getTeamsPlayingInGame().get(0).getTeamName());
//        System.out.println(simulationContext.getTeamsPlayingInGame().get(0).getPlayers().size());
//        System.out.println(simulationContext.getTeamsPlayingInGame().get(1).getTeamName());
//        System.out.println(simulationContext.getTeamsPlayingInGame().get(1).getPlayers().size());
//        System.out.println(simulationContext.getInjurySystem());
//        System.out.println(simulationContext.getGameConfig());
//
////        for (ITeam team : simulationContext.getTeamsPlayingInGame()) {
////            IInjury injury = simulationContext.getInjurySystem();
////            injury.checkTeamInjury(simulationContext.getGameConfig(), team);
////            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
////        }
//
//        injuryCheckState = new InjuryCheckState(simulationContext);
//        injuryCheckState.seasonStateProcess();
//        System.out.println(injuryCheckState.getSimulationContext().getTeamsPlayingInGame());
//
//
////        for (ITeam team : simulationContext.getTeamsPlayingInGame()) {
////            IInjury injury = simulationContext.getInjurySystem();
////            injury.checkTeamInjury(simulationContext.getGameConfig(), team);
////            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
////        }
    }

    @Test
    public void seasonStateExitProcessTest() {
//        injuryCheckState.seasonStateExitProcess();
//        Assertions.assertTrue(injuryCheckState.getSimulationContext().getCurrentSimulation() == injuryCheckState.getSimulationContext().getPersistsSeason());
        ILeagueObjectModel league = model20TeamMocks.getLeagueData();
        ArrayList<IStandings> standings = model20TeamMocks.getGeneralStandings();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();

        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
        LocalDate seasonStartDate = LocalDate.of(2020, 10, 1);
        LocalDate regularSeasonEndDate = LocalDate.of(2021, 04, 3);
        LocalDate seasonEndDate = LocalDate.of(2021, 06, 1);
        LocalDate currentDate = LocalDate.now();
        long numberOfDays = DAYS.between(startOfSimulation, currentDate);
//        System.out.println(numberOfDays);
//
//        System.out.println(scheduler.getPlayOffScheduleRound1().get(0).getGameDate());
//        System.out.println(scheduler.getPlayOffScheduleRound1().get(14).getGameDate());
//
//
//        System.out.println(scheduler.getSeasonStartDate());
//        System.out.println(scheduler.getSeasonEndDate());
//        System.out.println(scheduler.getPlayOffStartDate());
//        System.out.println(scheduler.getFinalDay());

        scheduler.setSeasonStartDate(seasonStartDate);
        scheduler.setSeasonEndDate(regularSeasonEndDate);
        simulationContext.setPlayOffScheduleRound1(scheduler);
        simulationContext.setStartOfSimulation(startOfSimulation);
        simulationContext.setNumberOfDays((int) numberOfDays);
        simulationContext.setYear(2020);
//        simulationContext.set
//        simulationContext.setStartOfSimulation(startOfSimulation);
//        agingState = new AgingState(simulationContext);
//        agingState.seasonStateExitProcess();


//        scheduler = simulationContext.getRegularScheduler();
//        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
//        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
//
//
//        if (currentDate.isAfter(scheduler.getSeasonStartDate()) && currentDate.isBefore(scheduler.getSeasonEndDate())) {
//            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
//        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())) {
//            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
//        } else {
//            LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
//            LocalDate regularSeasonEndDate = localDate.with(lastDayOfMonth())
//                    .with(previousOrSame(DayOfWeek.MONDAY));
//            if (currentDate.isBefore(regularSeasonEndDate) || currentDate.isEqual(regularSeasonEndDate)) {
//                simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
//            } else {
//                simulationContext.setCurrentSimulation(simulationContext.getAging());
//            }
//        }
    }
}
