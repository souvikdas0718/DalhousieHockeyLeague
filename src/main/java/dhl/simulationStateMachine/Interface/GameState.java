package dhl.simulationStateMachine.Interface;

public interface GameState {

    void stateEntryProcess();
    void stateProcess() throws Exception;
    void stateExitProcess();

}

