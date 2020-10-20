package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class GeneratePlayOffSchedule implements ISimulationSeasonState {

    public GeneratePlayOffSchedule(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // use NHL Stanley Cup rules to generate playoff rules
    }

    @Override
    public void seasonStateExitProcess() {
        // training
    }
}
