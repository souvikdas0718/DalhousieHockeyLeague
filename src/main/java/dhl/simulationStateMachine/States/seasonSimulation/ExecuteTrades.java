package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class ExecuteTrades implements ISimulationSeasonState {
    public ExecuteTrades(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() throws Exception {
        // calculate trade loss point
    }

    @Override
    public void seasonStateExitProcess() {

    }
}
