package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class AdvanceToNextSeasonState implements ISimulationSeasonState {
    private static final int ENDOFSEASONMONTH = 9;
    private static final int ENDOFSEASONDAY = 29;
    private static final int TOTALDAYSINAYEAR = 365;
    private static final int YEAR = 1;
    private static final Logger logger = LogManager.getLogger(AdvanceToNextSeasonState.class);
    SimulationContext simulationContext;

    public AdvanceToNextSeasonState(SimulationContext simulationContext) {
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
        logger.info("Into the state process of Advance to next season");
        simulationContext.setYear(simulationContext.getYear() + YEAR);
        LocalDate endOfSeason = LocalDate.of(simulationContext.getYear(), ENDOFSEASONMONTH, ENDOFSEASONDAY);
        simulationContext.setEndOfSimulation(endOfSeason);
        simulationContext.setNumberOfDays(TOTALDAYSINAYEAR);
        AgingState.agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Adv to next season");
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
    }
}
