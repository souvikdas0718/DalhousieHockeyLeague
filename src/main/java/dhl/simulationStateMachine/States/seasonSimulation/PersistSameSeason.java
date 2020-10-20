package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class PersistSameSeason implements ISimulationSeasonState {
    public PersistSameSeason(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // save the data in the db
    }

    @Override
    public void seasonStateExitProcess() {
        // call advanced time class
    }
}
