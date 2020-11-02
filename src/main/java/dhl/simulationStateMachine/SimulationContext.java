package dhl.simulationStateMachine;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.Interface.IUpdateUserTeamRoster;
import dhl.simulationStateMachine.States.seasonSimulation.*;
import dhl.trade.Interface.ITradingEngine;
import dhl.trade.TradingEngine;

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

    boolean gameInProgress;
    IGameConfig gameConfig;
    ILeagueObjectModel inMemoryLeague;
    ITeam userTeam;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;

//    ITeam selectedTeam;
//    public GameContext() {
//        currentState = importState;
//        gameInProgress = true;
//    }

    public SimulationContext(GameContext gameState) {
//      seasonSimulationState = new SeasonSimulationState(this);
        userTeam = gameState.getSelectedTeam();
        advanceTime = new AdvanceTime(this);
        aging = new Aging(this);
        executeTrades = new ExecuteTrades(this);
        playoffSchedule = new GeneratePlayOffSchedule(this);
        initializeSeason = new InitializeSeason(this);
        injuryCheck = new InjuryCheck(this);
        persistsSeason = new PersistSameSeason(this);
        persistsSeason = new PersistSeason(this);
        simulateGame = new SimulateGame(this);
        training = new Training(this);
        currentSimulation = initializeSeason;
        gameInProgress = true;
        ioObject = new UserInputOutput();
        IUpdateUserTeamRoster updateUserTeamRoster = new UpdateUserTeamRoster(ioObject);
        tradeEngine = new TradingEngine(gameConfig, inMemoryLeague, userTeam, ioObject, updateUserTeamRoster);
    }

    public ITradingEngine getTradeEngine() {
        return tradeEngine;
    }

    public IUserInputOutput getIoObject() {
        return ioObject;
    }

    public ITeam getUserTeam() {
        return userTeam;
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
