package dhl.simulationStateMachine.States;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class SeasonSimulationState implements ISimulationSeasonState {

    SimulationContext ongoingSimulation;

    public SeasonSimulationState(SimulationContext simulationContext){
        ongoingSimulation = simulationContext;
    }

    @Override
    public void startSeasonSimulation(int seasonNumber) {
        System.out.println("Season -"+ seasonNumber+ " is simulated");
    }
}