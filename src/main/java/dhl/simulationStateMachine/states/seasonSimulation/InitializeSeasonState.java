package dhl.simulationStateMachine.states.seasonSimulation;

import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.simulationStateMachine.states.seasonScheduler.Scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class InitializeSeasonState implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    IScheduler scheduler = new Scheduler();

    public InitializeSeasonState(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        LocalDate simulationStartDate = LocalDate.of(ourSeasonGame.getYear(), 9, 30);
        ourSeasonGame.setStartOfSimulation(simulationStartDate);
        scheduler.generateTeamList(ourSeasonGame.getInMemoryLeague());
        scheduler.generateTeamSchedule(ourSeasonGame.getInMemoryLeague());
        LocalDate localDate = LocalDate.of(ourSeasonGame.getYear() + 1, 03, 01);
        LocalDate reguarSeasonEndDate = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(ourSeasonGame.getStartOfSimulation(), reguarSeasonEndDate);
        ourSeasonGame.setRegularScheduler(scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getAdvanceTime());
    }
}
