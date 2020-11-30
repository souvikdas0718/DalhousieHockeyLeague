package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class AdvanceTimeState implements ISimulationSeasonState {
    private static final Month PLAYERDRAFTMONTH = Month.JULY;
    private static final int PLAYERDRAFTDATE = 15;
    private static final int DAY = 1;
    private static final Month APRILMONTH = Month.APRIL;
    private static final Logger logger = LogManager.getLogger(AdvanceTimeState.class);

    SimulationContext simulationContext;

    public AdvanceTimeState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of Advance Time State season");
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays() + 1);
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Advance Time State season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.getMonth() == APRILMONTH) {
            LocalDate endOfRegularSeasonDate = currentDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
            if (currentDate.minusDays(DAY).equals(endOfRegularSeasonDate.plusDays(DAY))) {
                logger.debug("Simulating to GeneratePlayOffSchedule state");
                simulationContext.setCurrentSimulation(simulationContext.getPlayoffSchedule());
            } else {
                logger.debug("Simulating to Training state");
                simulationContext.setCurrentSimulation(simulationContext.getTraining());
            }
        } else if (currentDate.getMonth() == PLAYERDRAFTMONTH && currentDate.getDayOfMonth() == PLAYERDRAFTDATE) {
            logger.debug("Simulating to PlayerDraft state");
            simulationContext.setCurrentSimulation(simulationContext.getPlayerDraft());
        } else {
            logger.debug("Simulating to Training state");
            simulationContext.setCurrentSimulation(simulationContext.getTraining());
        }
    }
}
