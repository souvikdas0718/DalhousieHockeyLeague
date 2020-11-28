package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class GeneratePlayOffScheduleState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IScheduler scheduler;
    IUserInputOutput userInputOutput;


    public GeneratePlayOffScheduleState(SimulationContext simulationContext) {
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
        userInputOutput.printMessage("Into the state process of General Playoffs season");
        scheduler = simulationContext.getRegularScheduler();
        LocalDate playOffStartDate = LocalDate.of(simulationContext.getYear(), 04, 01);
        LocalDate playOffStarts = playOffStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)).with(
                TemporalAdjusters.next(DayOfWeek.SATURDAY));
        scheduler.setPlayOffStartDate(playOffStarts);
        scheduler.playOffs(scheduler.getGameStandings(), simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of General Playoffs season");
        simulationContext.setCurrentSimulation(simulationContext.getTraining());
    }
}

