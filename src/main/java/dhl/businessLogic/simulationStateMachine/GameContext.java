package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.factory.GameStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class GameContext implements IGameContext {
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
    private static final Logger logger = LogManager.getLogger(GameContext.class);

    public GameContext() {
        GameStateAbstractFactory gameStateFactory = GameStateAbstractFactory.instance();
        importState = gameStateFactory.createImportState(this);
        loadTeamState = gameStateFactory.createLoadTeamState(this);
        simulateState = gameStateFactory.createSimulateState(this);
        createTeamState = gameStateFactory.createCreateTeamState(this);
        currentState = importState;
        gameInProgress = true;
        userInputOutput = IUserInputOutput.getInstance();
        year = LocalDate.now().getYear();
        logger.debug("Made Game context Object");
    }

    public void setGameState(IGameState newState) {
        this.currentState = newState;
    }

    public void stateEntryProcess() {
        logger.debug("running Entry Process for class: " + currentState.getClass().getName());
        currentState.stateEntryProcess();
    }

    public void stateProcess() throws Exception {
        logger.debug("running State Process for class: " + currentState.getClass().getName());
        currentState.stateProcess();
    }

    public void stateExitProcess() {
        logger.debug("running Exit Process for class: " + currentState.getClass().getName());
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

    public IGameState getCurrentState() {
        return currentState;
    }
}
