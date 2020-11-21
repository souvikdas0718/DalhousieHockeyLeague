package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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
        LocalDate playOffStartDate = LocalDate.of(simulationContext.getYear(), 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)).with(
                TemporalAdjusters.next(DayOfWeek.SATURDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(scheduler.getGameStandings(), simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
    }
}

