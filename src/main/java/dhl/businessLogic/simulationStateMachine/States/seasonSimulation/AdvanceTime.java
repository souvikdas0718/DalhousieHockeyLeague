package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class AdvanceTime implements ISimulationSeasonState {

    public AdvanceTime(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // increment day by one
    }

    @Override
    public void seasonStateExitProcess() {
        // if end of the regular season
        //genearate playoff scehdule
        //else
        // training
    }
}
