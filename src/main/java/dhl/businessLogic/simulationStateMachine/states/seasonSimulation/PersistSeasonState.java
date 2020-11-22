package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;

import java.time.LocalDate;

public class PersistSeasonState implements ISimulationSeasonState {
    SimulationContext simulationContext;

    public PersistSeasonState(SimulationContext simulationContext) {
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

    }

    @Override
    public void seasonStateProcess() {
        // save the data in the db
    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.isBefore(LocalDate.of(simulationContext.getYear()+1, 9, 29))) {
            simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        } else {
            //break the loop remarking an end of season
            simulationContext.setCurrentSimulation(simulationContext.getInitializeSeason());
        }
    }
}

