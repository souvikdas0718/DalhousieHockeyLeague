package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class AgingState implements ISimulationSeasonState {
    public static Logger logger = LogManager.getLogger(AgingState.class);
    static AgingAbstractFactory agingFactory;
    SimulationContext simulationContext;

    public AgingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        agingFactory = AgingAbstractFactory.instance();
    }

    static void agingCalculation(SimulationContext simulationContext) {
        ILeagueSchedule leagueSchedule = (LeagueSchedule) agingFactory.createLeagueSchedule(simulationContext.getInMemoryLeague());

        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        leagueSchedule.initiateAging(simulationContext.getNumberOfDays(), currentDate);

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
                logger.debug("Stanley Cup winner determined. So, advancing to next Season");
                simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
            } else {
                logger.debug("Stanley Cup winner not determined. So, moving to Persist Same Season");
                simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
            }
        }
    }
}
