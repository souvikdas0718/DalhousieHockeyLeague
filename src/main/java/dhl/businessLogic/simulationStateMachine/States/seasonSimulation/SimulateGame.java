package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

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
