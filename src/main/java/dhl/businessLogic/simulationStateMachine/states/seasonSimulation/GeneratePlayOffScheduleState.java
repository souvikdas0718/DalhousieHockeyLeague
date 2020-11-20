package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class GeneratePlayOffScheduleState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IScheduler scheduler;


    public GeneratePlayOffScheduleState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        scheduler = simulationContext.getRegularScheduler();
        scheduler.playOffs(scheduler.getGameStandings(), simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
    }
}

