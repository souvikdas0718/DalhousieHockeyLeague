package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.CreateTeamState;
import dhl.businessLogic.simulationStateMachine.states.ImportState;
import dhl.businessLogic.simulationStateMachine.states.LoadTeamState;
import dhl.businessLogic.simulationStateMachine.states.SimulateState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.LocalDate;

public class GameContext {
    IGameState importState;
    IGameState loadTeamState;
    IGameState simulateState;
    IGameState createTeamState;
    IGameState currentState;
    boolean gameInProgress;
    ILeagueObjectModel inMemoryLeague;
    ITeam selectedTeam;
    IGameConfig gameConfig;
    IUserInputOutput userInputOutput;
    int year;

    public GameContext() {
        importState = new ImportState(this);
        loadTeamState = new LoadTeamState(this);
        simulateState = new SimulateState(this);
        createTeamState = new CreateTeamState(this);
        currentState = importState;
        gameInProgress = true;
        userInputOutput = IUserInputOutput.getInstance();
        year = LocalDate.now().getYear();
    }

    public void setGameState(IGameState newState) {
        this.currentState = newState;
    }

    public void stateEntryProcess() {
        currentState.stateEntryProcess();
    }

    public void stateProcess() throws Exception {
        userInputOutput.printMessage("Into the state process of game context");
        currentState.stateProcess();
    }

    public void stateExitProcess() {
        userInputOutput.printMessage("Into the state process exit of game context");
        currentState.stateExitProcess();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public ILeagueObjectModel getInMemoryLeague() {
        return inMemoryLeague;
    }

    public void setInMemoryLeague(ILeagueObjectModel newInMemoryLeague) {
        inMemoryLeague = newInMemoryLeague;
    }

    public ITeam getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(ITeam selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public IGameState getSimulateState() {
        return simulateState;
    }

    public IGameState getImportState() {
        return importState;
    }

    public IGameState getLoadTeamState() {
        return loadTeamState;
    }

    public IGameState getCreateTeamState() {
        return createTeamState;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameStatus) {
        gameInProgress = gameStatus;
    }

    public IGameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(IGameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }
}
