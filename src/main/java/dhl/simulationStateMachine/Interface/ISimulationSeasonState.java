package dhl.simulationStateMachine.Interface;

public interface ISimulationSeasonState {

    //comment out this method
    //void startSeasonSimulation(int seasonNumber);

    void seasonStateEntryProcess();

    void seasonStateProcess() throws Exception;

    void seasonStateExitProcess();

}
