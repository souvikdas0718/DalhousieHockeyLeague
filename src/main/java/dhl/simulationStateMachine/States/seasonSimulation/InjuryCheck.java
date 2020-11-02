package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class InjuryCheck implements ISimulationSeasonState {
    public InjuryCheck(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // checks injuries
    }

    @Override
    public void seasonStateExitProcess() {
        // returns to the state where it checks unplayed scheduled games
    }
}
