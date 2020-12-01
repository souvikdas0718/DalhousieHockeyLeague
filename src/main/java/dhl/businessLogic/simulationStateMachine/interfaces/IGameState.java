package dhl.businessLogic.simulationStateMachine.interfaces;

public interface IGameState {

    void stateEntryProcess();

    void stateProcess() throws Exception;

    void stateExitProcess();

}

