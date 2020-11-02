package dhl.simulationStateMachine.states.seasonSimulation;

import dhl.database.PlayerDB;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.Aging;
import dhl.leagueModel.AgingSystem;
import dhl.leagueModel.RetirementSystem;
import dhl.leagueModel.interfaceModel.IAging;
import dhl.leagueModel.interfaceModel.IAgingSystem;
import dhl.leagueModel.interfaceModel.IRetirementSystem;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.time.LocalDate;

public class AgingState implements ISimulationSeasonState {
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
            System.out.println("Error occured while aging");
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
