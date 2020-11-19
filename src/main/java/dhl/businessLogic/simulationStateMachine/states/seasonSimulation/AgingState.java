package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;



import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.Retirement;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.database.PlayerDB;
import dhl.database.interfaceDB.IPlayerDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class AgingState implements ISimulationSeasonState {
    public static Logger log = LogManager.getLogger(AgingState.class);
    SimulationContext simulationContext;

    public AgingState(SimulationContext simulationContext) {

    }

    static void agingCalculation(SimulationContext simulationContext) {
        IAging aging = new Aging(simulationContext.getGameConfig());
        IPlayerDB playerDB = new PlayerDB();
        IRetirement retirement = new Retirement(playerDB, simulationContext.getInMemoryLeague());
        ILeagueSchedule leagueSchedule = new LeagueSchedule(aging, retirement, simulationContext.getInjurySystem(), simulationContext.getInMemoryLeague(), simulationContext.getNumberOfDays(), playerDB);
        try {
            leagueSchedule.initiateAging();
        } catch (Exception e) {
            log.error("Error occured while aging"+ e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        IScheduler scheduler = simulationContext.getRegularScheduler();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (scheduler.stanleyCupWinner(currentDate)) {
            simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
        } else {
            simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
        }
    }
}
