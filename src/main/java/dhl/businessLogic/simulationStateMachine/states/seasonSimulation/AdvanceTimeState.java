package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class AdvanceTimeState implements ISimulationSeasonState {
    private static final Month PLAYERDRAFTMONTH=Month.JULY;
    private static final int PLAYERDRAFTDATE=15;
    private static final Month APRILMONTH = Month.APRIL;

    SimulationContext simulationContext;

    public AdvanceTimeState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays() + 1);
    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.getMonth() == APRILMONTH) {
            LocalDate endOfRegularSeasonDate = currentDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
            if (currentDate.equals(endOfRegularSeasonDate)) {
                simulationContext.setCurrentSimulation(simulationContext.getPlayoffSchedule());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getTraining());
            }
        }else if(currentDate.getMonth() == PLAYERDRAFTMONTH && currentDate.getDayOfMonth() == PLAYERDRAFTDATE){
            simulationContext.setCurrentSimulation(simulationContext.getPlayerDraft());
        }
        else {
            simulationContext.setCurrentSimulation(simulationContext.getTraining());
        }
    }
}
