package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;

public class PersistSeason implements IGameState {
    @Override
    public void stateEntryProcess() {

    }

    @Override
    public void stateProcess() throws Exception {
        // save the data in the db
    }

    @Override
    public void stateExitProcess() {
        //end
    }
}
