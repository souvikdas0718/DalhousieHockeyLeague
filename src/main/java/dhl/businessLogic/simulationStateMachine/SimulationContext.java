package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import dhl.businessLogic.teamRosterUpdater.UpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class SimulationContext implements ISimulationSeasonState {

    ISimulationSeasonState seasonSimulationState;
    ISimulationSeasonState currentSimulation;
    ISimulationSeasonState advanceTime;
    ISimulationSeasonState advanceToNextSeason;
    ISimulationSeasonState aging;
    ISimulationSeasonState executeTrades;
    ISimulationSeasonState playoffSchedule;
    ISimulationSeasonState initializeSeason;
    ISimulationSeasonState injuryCheck;
    ISimulationSeasonState persistsSeason;
    ISimulationSeasonState persistsSameSeason;
    ISimulationSeasonState simulateGame;
    ISimulationSeasonState training;
    ISimulationSeasonState playerDraft;
    IScheduler scheduler;
    IScheduler regularScheduler;
    IScheduler playOffScheduleRound1;
    IScheduler finalSchedule;
    ITeamRosterUpdater updateUserTeamRoster;
    List<IStandings> standings;
    ISeasonSchedule matchToSimulate;

    boolean seasonInProgress;
    IGameConfig gameConfig;

    ILeagueObjectModel inMemoryLeague;
    ITeam userTeam;
    IUserInputOutput ioObject;
    int numberOfDays;
    int year;
    LocalDate startOfSimulation;
    LocalDate endOfSimulation;
    LocalDate seasonStartDate;
    LocalDate seasonEndDate;
    LocalDate playOffStartDate;
//    LocalDate currentDate;
    LocalDate finalDay;
    int daysSinceLastTraining;
    List<ITeam> teamsPlayingInGame;
    IInjury injury;
    ITradingEngine tradeEngine;

    public SimulationContext(GameContext gameState) {
        SimulationStateAbstractFactory factory = SimulationStateAbstractFactory.instance();
        advanceTime = factory.getAdvanceTimeState(this);
        advanceToNextSeason = factory.getAdvanceToNextSeasonState(this);
        aging = factory.getAgingState(this);
        executeTrades = factory.getExecuteTradesState(this);
        playoffSchedule = factory.getGeneratePlayoffScheduleState(this);
        initializeSeason = factory.getInitializeSeasonState(this);
        injuryCheck = factory.getInjuryCheckState(this);
        persistsSeason = factory.getPersistSeasonState(this);
        persistsSameSeason = factory.getPersistSameSeasonState(this);
        simulateGame = factory.getSimulateGameState(this);
        training = factory.getTrainingState(this);
        playerDraft = factory.getPlayerDraftState(this);

        userTeam = gameState.getSelectedTeam();
        currentSimulation = initializeSeason;
        seasonInProgress = true;
        ioObject = IUserInputOutput.getInstance();
        updateUserTeamRoster = new UpdateUserTeamRoster(ioObject);
        daysSinceLastTraining = 0;
        teamsPlayingInGame = new ArrayList<>();
        injury = new Injury();
        year = gameState.getYear();
        seasonStartDate = LocalDate.of(this.getYear(), 10, 01);
        LocalDate seasonEndMonth = LocalDate.of(gameState.getYear() + 1, 04, 01);
        LocalDate regularSeasonEndDate = seasonEndMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        seasonEndDate = regularSeasonEndDate;
        LocalDate playOffStartMonth = LocalDate.of(gameState.getYear() + 1, 04, 01);
        LocalDate playOffStartsDay = playOffStartMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        playOffStartDate = playOffStartsDay;
        finalDay = LocalDate.of(gameState.getYear() + 1, 06, 01);
        scheduler = new Scheduler();
        scheduler.setFinalDay(finalDay);
        scheduler.setSeasonEndDate(seasonEndDate);
        scheduler.setSeasonStartDate(seasonStartDate);
        scheduler.setPlayOffStartDate(playOffStartDate);
        startOfSimulation = LocalDate.of(year, 9, 30);

    }

    public ISeasonSchedule getMatchToSimulate() {
        return matchToSimulate;
    }

    public void setMatchToSimulate(ISeasonSchedule matchToSimulate) {
        this.matchToSimulate = matchToSimulate;
    }

    public LocalDate getSeasonStartDate() {
        return seasonStartDate;
    }

    public LocalDate getSeasonEndDate() {
        return seasonEndDate;
    }

    public LocalDate getPlayOffStartDate() {
        return playOffStartDate;
    }

    public LocalDate getFinalDay() {
        return finalDay;
    }

    public void setSeasonStartDate(LocalDate seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public void setSeasonEndDate(LocalDate seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public void setPlayOffStartDate(LocalDate playOffStartDate) {
        this.playOffStartDate = playOffStartDate;
    }

    public void setFinalDay(LocalDate finalDay) {
        this.finalDay = finalDay;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public IScheduler getFinalSchedule() {
        return finalSchedule;
    }

    public void setFinalSchedule(IScheduler finalSchedule) {
        this.finalSchedule = finalSchedule;
    }

    public List<IStandings> getStandings() {
        return standings;
    }

    public void setStandings(List<IStandings> standings) {
        this.standings = standings;
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

    public IInjury getInjurySystem() {
        return injury;
    }

    public void setInjurySystem(IInjury injury) {
        this.injury = injury;
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

    public ISimulationSeasonState getPersistsSameSeason() {
        return persistsSameSeason;
    }

    public void setPersistsSameSeason(ISimulationSeasonState persistsSameSeason) {
        this.persistsSameSeason = persistsSameSeason;
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

    public ISimulationSeasonState getPlayerDraft() {
        return playerDraft;
    }

    public void setPlayerDraft(ISimulationSeasonState playerDraft) {
        this.playerDraft = playerDraft;
    }

    public boolean isSeasonInProgress() {
        return seasonInProgress;
    }

    public void setSeasonInProgress(boolean seasonInProgress) {
        this.seasonInProgress = seasonInProgress;
    }

    public ILeagueObjectModel getInMemoryLeague() {
        return inMemoryLeague;
    }

    public void setInMemoryLeague(ILeagueObjectModel inMemoryLeague) {
        this.inMemoryLeague = inMemoryLeague;
    }

    public LocalDate getEndOfSimulation() {
        return endOfSimulation;
    }

    public void setEndOfSimulation(LocalDate endOfSimulation) {
        this.endOfSimulation = endOfSimulation;
    }
}
