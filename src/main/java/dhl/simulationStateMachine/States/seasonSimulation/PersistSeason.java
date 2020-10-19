package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class PersistSeason implements ISimulationSeasonState {
    public PersistSeason(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() throws Exception {
        // save the data in the db
    }

    @Override
    public void seasonStateExitProcess() {
        //end
    }
}
