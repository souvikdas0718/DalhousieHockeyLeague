package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class AdvanceTime implements ISimulationSeasonState {

    public AdvanceTime(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() throws Exception {
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
