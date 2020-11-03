package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

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
    }
}
