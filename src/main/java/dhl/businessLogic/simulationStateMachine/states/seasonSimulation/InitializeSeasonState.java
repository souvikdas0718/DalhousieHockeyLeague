package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class InitializeSeasonState implements ISimulationSeasonState {
    private static final int SIMULATESTARTMONTH = 9;
    private static final int SIMULATESTARTDAY = 30;
    private static final int REGULARSEASONENDMONTH = 3;
    private static final int REGULARSEASONENDDAY = 1;
    private static final int DAY = 1;
    private static final int YEAR = 1;
    public static Logger logger = LogManager.getLogger(InitializeSeasonState.class);
    SimulationContext simulationContext;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;
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
        logger.info("Into the state process of Initialize season");
        LocalDate simulationStartDate = LocalDate.of(simulationContext.getYear(), SIMULATESTARTMONTH, SIMULATESTARTDAY);
        simulationContext.setStartOfSimulation(simulationStartDate);
        scheduler.generateTeamList(simulationContext.getInMemoryLeague());
        scheduler.generateTeamSchedule(simulationContext.getInMemoryLeague());
        LocalDate localDate = LocalDate.of(simulationContext.getYear() + YEAR, REGULARSEASONENDMONTH, REGULARSEASONENDDAY);
        LocalDate regularSeasonEndDate = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(simulationContext.getStartOfSimulation().plusDays(DAY), regularSeasonEndDate);
        simulationContext.setRegularScheduler(scheduler);
        standingSystem.createStandings(simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Initialize season");
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
    }
}
