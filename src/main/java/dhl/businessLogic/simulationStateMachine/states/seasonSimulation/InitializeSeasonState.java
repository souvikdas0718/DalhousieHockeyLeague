package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.Standings;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class InitializeSeasonState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;
    IStandings standings;
    IStandingSystem standingSystem;

    public InitializeSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        scheduler = schedulerAbstractFactory.getScheduler();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
        userInputOutput = IUserInputOutput.getInstance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of Initialize season");
        LocalDate simulationStartDate = LocalDate.of(simulationContext.getYear(), 9, 30);
        simulationContext.setStartOfSimulation(simulationStartDate);
        scheduler.generateTeamList(simulationContext.getInMemoryLeague());
        scheduler.generateTeamSchedule(simulationContext.getInMemoryLeague());
        LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 03, 01);
        LocalDate regularSeasonEndDate = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(simulationContext.getStartOfSimulation().plusDays(1), regularSeasonEndDate);
        simulationContext.setRegularScheduler(scheduler);
        standingSystem.createStandings(simulationContext.getInMemoryLeague());

    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Initialize season");
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
    }
}
