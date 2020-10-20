package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class SimulateGame implements ISimulationSeasonState {
    public SimulateGame(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
            // simulate one game win/loss
    }

    @Override
    public void seasonStateExitProcess() {
        // check injury
    }
}
