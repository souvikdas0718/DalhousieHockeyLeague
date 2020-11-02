package dhl.simulationStateMachine.states.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.time.LocalDate;

public class PersistSeason implements ISimulationSeasonState {
    SimulationContext ourSeasonGame;

    public PersistSeason(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {

    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation = ourSeasonGame.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(ourSeasonGame.getNumberOfDays());
        if (currentDate.equals(LocalDate.of(ourSeasonGame.getYear(), 9, 29))) {

        } else {
            ourSeasonGame.setCurrentSimulation(ourSeasonGame.getAdvanceTime());
        }
    }
}
