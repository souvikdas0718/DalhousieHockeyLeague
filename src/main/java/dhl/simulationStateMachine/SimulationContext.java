package dhl.simulationStateMachine;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.States.seasonSimulation.*;

public class SimulationContext implements ISimulationSeasonState{

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
//    ILeagueObjectModel inMemoryLeague;
//    ITeam selectedTeam;
//    public GameContext() {
//        currentState = importState;
//        gameInProgress = true;
//    }

    public SimulationContext(){
//        seasonSimulationState = new SeasonSimulationState(this);
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
    }

    // delete parts
//    public void startSeasonSimulation(int seasonNumber){
//        currentSimulation.startSeasonSimulation(seasonNumber);
//    }

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
}
