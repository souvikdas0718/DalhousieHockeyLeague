package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.AgingSystem;
import dhl.businessLogic.aging.Interface.IAging;
import dhl.businessLogic.aging.Interface.IAgingSystem;
import dhl.businessLogic.aging.Interface.IRetirementSystem;
import dhl.businessLogic.aging.RetirementSystem;
import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.database.PlayerDB;
import dhl.database.interfaceDB.IPlayerDB;


import java.time.LocalDate;

public class AgingState implements ISimulationSeasonState {
    private static Logger log = LoggerFactory.getLogger(AgingState.class);
    SimulationContext simulationContext;

    public AgingState(SimulationContext simulationContext) {

    }

    static void agingCalculation(SimulationContext simulationContext) {
        IAgingSystem agingSystem = new AgingSystem(simulationContext.getGameConfig());
        IPlayerDB playerDB = new PlayerDB();
        IRetirementSystem retirementSystem = new RetirementSystem(playerDB, simulationContext.getInMemoryLeague());
        IAging aging = new Aging(agingSystem, retirementSystem, simulationContext.getInjurySystem(), simulationContext.getInMemoryLeague(), simulationContext.getNumberOfDays(), playerDB);
        try {
            aging.initiateAging();
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
