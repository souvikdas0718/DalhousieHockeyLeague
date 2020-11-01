package dhl.simulationStateMachine.States.seasonSimulation;


import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class InitializeSeason implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    public InitializeSeasonState(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        //TODO Generate full season schedule
        LocalDate simulationStartDate = LocalDate.of(2020,9,30);
        ourSeasonGame.setStartOfSimulation(simulationStartDate);
    }

    @Override
    public void seasonStateExitProcess() {
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getAdvanceTime());
    }
}
