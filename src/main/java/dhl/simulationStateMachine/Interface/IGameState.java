package dhl.simulationStateMachine.Interface;

public interface IGameState {

    void stateEntryProcess();

    void stateProcess() throws Exception;

    void stateExitProcess();

}
