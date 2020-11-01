package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class AdvanceTime implements ISimulationSeasonState {

    SimulationContext simulationContext;

    public AdvanceTimeState(SimulationContext simulationContext) {
        this.simulationContext=simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays()+1);
    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation=simulationContext.getStartOfSimulation();
        LocalDate currentDate= startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if(currentDate.getMonth()== Month.APRIL){
            LocalDate endOfRegularSeasonDate= currentDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
            if(currentDate.equals(endOfRegularSeasonDate) ){
                simulationContext.setCurrentSimulation(simulationContext.getPlayoffSchedule());
            }
            else {
                simulationContext.setCurrentSimulation(simulationContext.getTraining());
            }
        }
        else {
            simulationContext.setCurrentSimulation(simulationContext.getTraining());
        }
    }
}
