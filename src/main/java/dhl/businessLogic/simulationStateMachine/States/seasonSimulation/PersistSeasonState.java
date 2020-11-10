package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

import java.time.LocalDate;

public class PersistSeasonState implements ISimulationSeasonState {
    SimulationContext ourSeasonGame;

    public PersistSeasonState(SimulationContext simulationContext) {
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

