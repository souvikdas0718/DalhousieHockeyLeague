package dhl.simulationStateMachine.States.seasonSimulation;


import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class InitializeSeason implements ISimulationSeasonState {
    public InitializeSeason(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() throws Exception {

    }

    @Override
    public void seasonStateExitProcess() {
    //Advance Time by one day
    }
}
