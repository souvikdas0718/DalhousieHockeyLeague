package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class AdvanceTimeState implements ISimulationSeasonState {

    SimulationContext simulationContext;

    public AdvanceTimeState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays() + 1);
    }

    @Override
    public void seasonStateExitProcess() {
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
