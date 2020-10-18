package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;

public class InjuryCheck implements IGameState {
    @Override
    public void stateEntryProcess() {

    }

    @Override
    public void stateProcess() throws Exception {
        // checks injuries
    }

    @Override
    public void stateExitProcess() {
        // returns to the state where it checks unplayed scheduled games
    }
}
