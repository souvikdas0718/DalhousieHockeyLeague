package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;

public class Training implements IGameState {

    @Override
    public void stateEntryProcess() {

    }

    @Override
    public void stateProcess() throws Exception {
            // increment by one day
    }

    @Override
    public void stateExitProcess() {
            // any unplayed game scheduled
                    // yes then simulate game
                // no then check trade deadline
                    // no execute trade
                    // yes aging
    }
}
