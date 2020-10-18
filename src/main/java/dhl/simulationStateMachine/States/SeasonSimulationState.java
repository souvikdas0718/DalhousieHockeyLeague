package dhl.simulationStateMachine.States;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.simulationStateMachine.States.seasonSimulation.InitializeSeason;

public class SeasonSimulationState implements ISimulationSeasonState {
    SimulationContext ongoingSimulation;

    public SeasonSimulationState(SimulationContext simulationContext){
        ongoingSimulation = simulationContext;
    }

    @Override
    public void startSeasonSimulation(int seasonNumber) {

        System.out.println("Season Number:"+ seasonNumber+ " is simulated");
        InitializeSeason seasonSchedule = new InitializeSeason();
        //
        seasonSchedule.stateEntryProcess();
    }
}
