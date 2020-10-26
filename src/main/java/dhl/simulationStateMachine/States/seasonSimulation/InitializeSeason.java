package dhl.simulationStateMachine.States.seasonSimulation;


import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class InitializeSeason implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    public InitializeSeason(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {

    }

    @Override
    public void seasonStateExitProcess() {
    //Advance Time by one day
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getAdvanceTime());
    }
}
