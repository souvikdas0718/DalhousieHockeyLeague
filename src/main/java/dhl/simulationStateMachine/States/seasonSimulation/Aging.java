package dhl.simulationStateMachine.States.seasonSimulation;


import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class Aging implements ISimulationSeasonState {
    public Aging(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // increase agg by one day and append the days at the end of the season
    }

    @Override
    public void seasonStateExitProcess() {
            // check for stanley cup winner
                    // yes, advance to next season
                    // no, persist
    }
}
