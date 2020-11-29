package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.LocalDate;

public class AdvanceToNextSeasonState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;


    public AdvanceToNextSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        userInputOutput = IUserInputOutput.getInstance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of Adv to next season");
        simulationContext.setYear(simulationContext.getYear() + 1);
        LocalDate endOfSeason = LocalDate.of(simulationContext.getYear(), 9, 29);
        simulationContext.setEndOfSimulation(endOfSeason);
        simulationContext.setNumberOfDays(365);
        AgingState.agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Adv to next season");
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
    }
}
