package dhl.simulationStateMachine.states.seasonSimulation;

import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class GeneratePlayOffSchedule implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    IScheduler scheduler;

    public GeneratePlayOffSchedule(SimulationContext simulationContext) {
        this.ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        ourSeasonGame.getRegularScheduler();
        scheduler.playOffs(scheduler.getGameStandings(), ourSeasonGame.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getTraining());
    }
}
