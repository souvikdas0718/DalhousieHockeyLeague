package dhl.simulationStateMachine;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.States.SeasonSimulationState;

public class SimulationContext {

    ISimulationSeasonState seasonSimulationState;

    ISimulationSeasonState currentSimulation;

    public SimulationContext(){
        seasonSimulationState = new SeasonSimulationState(this);

        currentSimulation = seasonSimulationState;
    }

    // delete parts
    public void startSeasonSimulation(int seasonNumber){
        currentSimulation.startSeasonSimulation(seasonNumber);
    }
}
