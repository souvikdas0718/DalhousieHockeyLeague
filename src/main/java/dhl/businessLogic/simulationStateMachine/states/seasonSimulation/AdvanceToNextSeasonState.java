package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

import java.time.LocalDate;

public class AdvanceToNextSeasonState implements ISimulationSeasonState {

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
    public void seasonStateEntryProcess() {
        simulationContext.setYear(simulationContext.getYear() + 1);
        LocalDate endOfSeason = LocalDate.of(simulationContext.getYear() , 9, 29);
        simulationContext.setEndOfSimulation(endOfSeason);
        simulationContext.setNumberOfDays(365);
    }

    @Override
    public void seasonStateProcess() {
        AgingState.agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
    }
}
