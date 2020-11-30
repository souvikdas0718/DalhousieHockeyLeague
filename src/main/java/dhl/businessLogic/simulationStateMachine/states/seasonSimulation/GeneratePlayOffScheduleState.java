package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class GeneratePlayOffScheduleState implements ISimulationSeasonState {
    private static final int PLAYOFFSTARTMONTH = 4;
    private static final int PLAYOFFSTARTDAY = 1;
    private static final int YEAR = 1;
    public static Logger logger = LogManager.getLogger(GeneratePlayOffScheduleState.class);
    SimulationContext simulationContext;
    IScheduler scheduler;
    IUserInputOutput userInputOutput;
    StandingsAbstractFactory standingsAbstractFactory;
    IStandingSystem standingSystem;


    public GeneratePlayOffScheduleState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        userInputOutput = IUserInputOutput.getInstance();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of General Playoffs season");
        scheduler = simulationContext.getRegularScheduler();
        LocalDate playOffStartDate = LocalDate.of(simulationContext.getYear() + YEAR, PLAYOFFSTARTMONTH, PLAYOFFSTARTDAY);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).with(
                TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(standingSystem.getStandingsList(), simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of General Playoffs season");
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
    }
}

