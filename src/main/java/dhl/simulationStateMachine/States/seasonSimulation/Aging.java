package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;

public class Aging implements IGameState {
    @Override
    public void stateEntryProcess() {

    }

    @Override
    public void stateProcess() throws Exception {
        // increase agg by one day and append the days at the end of the season
    }

    @Override
    public void stateExitProcess() {
            // check for stanley cup winner
                    // yes, advance to next season
                    // no, persist
    }
}
