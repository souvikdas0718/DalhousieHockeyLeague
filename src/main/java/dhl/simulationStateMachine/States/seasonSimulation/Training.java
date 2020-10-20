package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class Training implements ISimulationSeasonState {

    public Training(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
            // increment by one day
    }

    @Override
    public void seasonStateExitProcess() {
            // any unplayed game scheduled
                    // yes then simulate game
                // no then check trade deadline
                    // no execute trade
                    // yes aging
    }
}
