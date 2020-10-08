package dhl.simulationStateMachine;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.GameState;
import dhl.simulationStateMachine.States.CreateTeamState;
import dhl.simulationStateMachine.States.ImportState;
import dhl.simulationStateMachine.States.LoadTeamState;
import dhl.simulationStateMachine.States.SimulateState;

public class GameContext {

    GameState importState;
    GameState loadTeamState;
    GameState simulateState;
    GameState createTeamState;
    GameState currentState;
    boolean gameinProgress;
    ILeagueObjectModel inMemoryLeague;
    ITeam selectedTeam;

    public GameContext(){
        importState = new ImportState(this);
        loadTeamState = new LoadTeamState(this);
        simulateState = new SimulateState(this);
        createTeamState = new CreateTeamState(this);

        currentState = importState;
        gameinProgress = true;
    }

    public void setGameState(GameState newState){
        this.currentState = newState;
    }

    public void stateEntryProcess(){
        currentState.stateEntryProcess();
    }

    public void stateProcess() throws Exception {
        currentState.stateProcess();
    }

    public void stateExitProcess(){
        currentState.stateExitProcess();
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

    public GameState getSimulateState() {
        return simulateState;
    }

    public GameState getImportState() {
        return importState;
    }

    public GameState getLoadTeamState() {
        return loadTeamState;
    }

    public GameState getCreateTeamState() {
        return createTeamState;
    }

    public boolean isGameinProgress() {
        return gameinProgress;
    }

    public void setGameinProgress(boolean gameStatus){
        gameinProgress = gameStatus;
    }

}
