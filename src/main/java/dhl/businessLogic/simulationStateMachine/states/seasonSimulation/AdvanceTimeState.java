package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class AdvanceTimeState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;

    public AdvanceTimeState(SimulationContext simulationContext) {
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
        userInputOutput.printMessage("Into the state process of Advance Time State season");
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays() + 1);
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Advance Time State season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.getMonth() == Month.APRIL) {
            LocalDate endOfRegularSeasonDate = currentDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
            if (currentDate.equals(endOfRegularSeasonDate)) {
                simulationContext.setCurrentSimulation(simulationContext.getPlayoffSchedule());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getTraining());
            }
        } else {
            simulationContext.setCurrentSimulation(simulationContext.getTraining());
        }
    }
}
