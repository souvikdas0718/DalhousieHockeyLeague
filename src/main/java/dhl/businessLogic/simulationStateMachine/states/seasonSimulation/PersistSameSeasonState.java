package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class PersistSameSeasonState implements ISimulationSeasonState {
    public PersistSameSeasonState(SimulationContext simulationContext) {
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
