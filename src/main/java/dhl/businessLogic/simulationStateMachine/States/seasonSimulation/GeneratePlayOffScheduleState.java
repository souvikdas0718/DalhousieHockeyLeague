package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class GeneratePlayOffScheduleState implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    IScheduler scheduler;

    public GeneratePlayOffScheduleState(SimulationContext simulationContext) {
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

