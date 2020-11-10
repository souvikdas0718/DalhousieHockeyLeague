package dhl.businessLogic.simulationStateMachine;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.aging.InjurySystem;
import dhl.businessLogic.aging.Interface.IInjurySystem;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.Interface.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.Interface.IUpdateUserTeamRoster;
import dhl.businessLogic.simulationStateMachine.States.seasonSimulation.*;
import dhl.businessLogic.trade.Interface.ITradingEngine;
import dhl.businessLogic.trade.TradingEngine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimulationContext implements ISimulationSeasonState {

    ISimulationSeasonState seasonSimulationState;
    ISimulationSeasonState currentSimulation;
    ISimulationSeasonState advanceTime;
    ISimulationSeasonState aging;
    ISimulationSeasonState executeTrades;
    ISimulationSeasonState playoffSchedule;
    ISimulationSeasonState initializeSeason;
    ISimulationSeasonState injuryCheck;
    ISimulationSeasonState persistsSeason;
    ISimulationSeasonState simulateGame;
    ISimulationSeasonState training;
    ISimulationSeasonState advanceToNextSeason;
    IScheduler regularScheduler;
    IScheduler playOffScheduleRound1;
    IUpdateUserTeamRoster updateUserTeamRoster;

    IStandingSystem standingSystem;

    boolean gameInProgress;
    IGameConfig gameConfig;

    ILeagueObjectModel inMemoryLeague;
    ITeam userTeam;
    IUserInputOutput ioObject;
    int numberOfDays;
    int year;
    LocalDate startOfSimulation;
    int daysSinceLastTraining;
    List<ITeam> teamsPlayingInGame;
    IInjurySystem injurySystem;
    ITradingEngine tradeEngine;
    public SimulationContext(GameContext gameState) {
        userTeam = gameState.getSelectedTeam();
        advanceTime = new AdvanceTimeState(this);
        aging = new AgingState(this);
        executeTrades = new ExecuteTradesState(this);
        playoffSchedule = new GeneratePlayOffScheduleState(this);
        initializeSeason = new InitializeSeasonState(this);
        injuryCheck = new InjuryCheckState(this);
        persistsSeason = new PersistSeasonState(this);
        simulateGame = new SimulateGameState(this);
        training = new TrainingState(this);
        currentSimulation = initializeSeason;
        gameInProgress = true;
        ioObject = new UserInputOutput();
        updateUserTeamRoster = new UpdateUserTeamRoster(ioObject);
        tradeEngine = new TradingEngine(gameConfig, inMemoryLeague, userTeam, ioObject, updateUserTeamRoster);
        daysSinceLastTraining = 0;
        teamsPlayingInGame = new ArrayList<>();
        injurySystem = new InjurySystem();
        year = 2020;
        startOfSimulation = LocalDate.of(year, 9, 30);
        advanceToNextSeason = new AdvanceToNextSeasonState(this);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public IStandingSystem getStandingSystem() {
        return standingSystem;
    }

    public void setStandingSystem(IStandingSystem standingSystem) {
        this.standingSystem = standingSystem;
    }

    public ISimulationSeasonState getAdvanceToNextSeason() {
        return advanceToNextSeason;
    }

    public IScheduler getRegularScheduler() {
        return regularScheduler;
    }

    public void setRegularScheduler(IScheduler regularScheduler) {
        this.regularScheduler = regularScheduler;
    }

    public IScheduler getPlayOffScheduleRound1() {
        return playOffScheduleRound1;
    }

    public void setPlayOffScheduleRound1(IScheduler playOffScheduleRound1) {
        this.playOffScheduleRound1 = playOffScheduleRound1;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDate getStartOfSimulation() {
        return startOfSimulation;
    }

    public void setStartOfSimulation(LocalDate startOfSimulation) {
        this.startOfSimulation = startOfSimulation;
    }

    public int getDaysSinceLastTraining() {
        return daysSinceLastTraining;
    }

    public void setDaysSinceLastTraining(int daysSinceLastTraining) {
        this.daysSinceLastTraining = daysSinceLastTraining;
    }

    public List<ITeam> getTeamsPlayingInGame() {
        return teamsPlayingInGame;
    }

    public void setTeamsPlayingInGame(List<ITeam> teamsPlayingInGame) {
        this.teamsPlayingInGame = teamsPlayingInGame;
    }

    public IInjurySystem getInjurySystem() {
        return injurySystem;
    }

    public void setInjurySystem(IInjurySystem injurySystem) {
        this.injurySystem = injurySystem;
    }

    public ITradingEngine getTradeEngine() {
        return tradeEngine;
    }

    public void setTradeEngine(ITradingEngine tradeEngine) {
        this.tradeEngine = tradeEngine;
    }

    public IUserInputOutput getIoObject() {
        return ioObject;
    }

    public void setIoObject(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
    }

    public ITeam getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(ITeam userTeam) {
        this.userTeam = userTeam;
    }

    public IGameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(IGameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void setSeasonGameState(ISimulationSeasonState newSeasonState) {
        this.currentSimulation = newSeasonState;
    }

    public void seasonStateEntryProcess() {
        currentSimulation.seasonStateEntryProcess();
    }

    public void seasonStateProcess() {
        currentSimulation.seasonStateProcess();
    }

    public void seasonStateExitProcess() {
        currentSimulation.seasonStateExitProcess();
    }

    public ISimulationSeasonState getSeasonSimulationState() {
        return seasonSimulationState;
    }

    public void setSeasonSimulationState(ISimulationSeasonState seasonSimulationState) {
        this.seasonSimulationState = seasonSimulationState;
    }

    public ISimulationSeasonState getCurrentSimulation() {
        return currentSimulation;
    }

    public void setCurrentSimulation(ISimulationSeasonState currentSimulation) {
        this.currentSimulation = currentSimulation;
    }

    public ISimulationSeasonState getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(ISimulationSeasonState advanceTime) {
        this.advanceTime = advanceTime;
    }

    public ISimulationSeasonState getAging() {
        return aging;
    }

    public void setAging(ISimulationSeasonState aging) {
        this.aging = aging;
    }

    public ISimulationSeasonState getExecuteTrades() {
        return executeTrades;
    }

    public void setExecuteTrades(ISimulationSeasonState executeTrades) {
        this.executeTrades = executeTrades;
    }

    public ISimulationSeasonState getPlayoffSchedule() {
        return playoffSchedule;
    }

    public void setPlayoffSchedule(ISimulationSeasonState playoffSchedule) {
        this.playoffSchedule = playoffSchedule;
    }

    public ISimulationSeasonState getInitializeSeason() {
        return initializeSeason;
    }

    public void setInitializeSeason(ISimulationSeasonState initializeSeason) {
        this.initializeSeason = initializeSeason;
    }

    public ISimulationSeasonState getInjuryCheck() {
        return injuryCheck;
    }

    public void setInjuryCheck(ISimulationSeasonState injuryCheck) {
        this.injuryCheck = injuryCheck;
    }

    public ISimulationSeasonState getPersistsSeason() {
        return persistsSeason;
    }

    public void setPersistsSeason(ISimulationSeasonState persistsSeason) {
        this.persistsSeason = persistsSeason;
    }

    public ISimulationSeasonState getSimulateGame() {
        return simulateGame;
    }

    public void setSimulateGame(ISimulationSeasonState simulateGame) {
        this.simulateGame = simulateGame;
    }

    public ISimulationSeasonState getTraining() {
        return training;
    }

    public void setTraining(ISimulationSeasonState training) {
        this.training = training;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public ILeagueObjectModel getInMemoryLeague() {
        return inMemoryLeague;
    }

    public void setInMemoryLeague(ILeagueObjectModel inMemoryLeague) {
        this.inMemoryLeague = inMemoryLeague;
    }
}
