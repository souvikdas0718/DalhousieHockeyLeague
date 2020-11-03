package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

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
