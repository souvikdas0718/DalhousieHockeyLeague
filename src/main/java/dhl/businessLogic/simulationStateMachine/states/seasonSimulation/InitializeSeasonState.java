package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class InitializeSeasonState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IScheduler scheduler;

    public InitializeSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        scheduler = new Scheduler();
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
        LocalDate simulationStartDate = LocalDate.of(simulationContext.getYear(), 9, 30);
        simulationContext.setStartOfSimulation(simulationStartDate);
        scheduler.generateTeamList(simulationContext.getInMemoryLeague());
        scheduler.generateTeamSchedule(simulationContext.getInMemoryLeague());
        LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 03, 01);
        LocalDate regularSeasonEndDate = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(simulationContext.getStartOfSimulation().plusDays(1), regularSeasonEndDate);
        simulationContext.setRegularScheduler(scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
    }
}
