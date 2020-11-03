package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

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
