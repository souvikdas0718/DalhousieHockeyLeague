package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;

public class AdvanceTime implements IGameState {
    @Override
    public void stateEntryProcess() {

    }

    @Override
    public void stateProcess() throws Exception {
            // increment day by one
    }

    @Override
    public void stateExitProcess() {
            // if end of the regular season
                //genearate playoff scehdule
            //else
                 // training
    }
}
