package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class AgingState implements ISimulationSeasonState {
    private static final Month PLAYERDRAFTMONTH = Month.JULY;
    private static final int PLAYERDRAFTDATE = 15;
    public static Logger logger = LogManager.getLogger(AgingState.class);
    static AgingAbstractFactory agingFactory;
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;

    public AgingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        agingFactory = AgingAbstractFactory.instance();
        userInputOutput = IUserInputOutput.getInstance();
    }

    void agingCalculation(SimulationContext simulationContext) {
        ILeagueSchedule leagueSchedule = agingFactory.createLeagueSchedule(simulationContext.getInMemoryLeague());
        try {
            LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
            LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
            userInputOutput.printMessage("Current date: " + currentDate);
            userInputOutput.printMessage("No of days: " + simulationContext.getNumberOfDays());
            leagueSchedule.initiateAging(simulationContext.getNumberOfDays(), currentDate);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of Aging State season");
        agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Aging State season");
        IScheduler scheduler = simulationContext.getRegularScheduler();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (null == scheduler) {
            logger.debug("No current Schedule. So, moving to Persist Same Season");
            simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
        } else {
            if (scheduler.stanleyCupWinner(currentDate)) {
                currentDate = LocalDate.of(currentDate.getYear(), PLAYERDRAFTMONTH, PLAYERDRAFTDATE);
                if (currentDate.getMonth() == PLAYERDRAFTMONTH && currentDate.getDayOfMonth() == PLAYERDRAFTDATE) {
                    logger.debug("Simulating to PlayerDraft state");
                    simulationContext.setCurrentSimulation(simulationContext.getPlayerDraft());
                }
            } else {
                logger.debug("Stanley Cup winner not determined. So, moving to Persist Same Season");
                simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
            }
        }
    }
}
