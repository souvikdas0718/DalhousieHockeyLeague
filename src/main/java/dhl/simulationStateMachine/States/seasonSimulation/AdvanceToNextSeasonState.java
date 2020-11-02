package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.database.PlayerDB;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.Aging;
import dhl.leagueModel.AgingSystem;
import dhl.leagueModel.RetirementSystem;
import dhl.leagueModel.interfaceModel.IAging;
import dhl.leagueModel.interfaceModel.IAgingSystem;
import dhl.leagueModel.interfaceModel.IRetirementSystem;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.time.LocalDate;

public class AdvanceToNextSeasonState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    public AdvanceToNextSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {
        simulationContext.setYear(simulationContext.getYear()+1);
        LocalDate endOfSeason = LocalDate.of(simulationContext.getYear()+1,9,29);
        simulationContext.setNumberOfDays(365);
    }

    @Override
    public void seasonStateProcess() {

        IAgingSystem agingSystem = new AgingSystem(simulationContext.getGameConfig());
        IPlayerDB playerDB= new PlayerDB();
        IRetirementSystem retirementSystem = new RetirementSystem(playerDB, simulationContext.getInMemoryLeague());
        IAging aging = new Aging(agingSystem, retirementSystem, simulationContext.getInjurySystem(), simulationContext.getInMemoryLeague(), simulationContext.getNumberOfDays(), playerDB);
        try{
            aging.initiateAging();
        } catch (Exception e) {
            System.out.println("Error occured while aging");
        }
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
    }
}
