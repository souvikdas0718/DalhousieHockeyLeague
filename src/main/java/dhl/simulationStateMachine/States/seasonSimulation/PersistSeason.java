package dhl.simulationStateMachine.States.seasonSimulation;

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
        //  ourSeasonGame.setYear(ourSeasonGame.getYear()+1);
        // save the data in the db
    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation=ourSeasonGame.getStartOfSimulation();
        LocalDate currentDate= startOfSimulation.plusDays(ourSeasonGame.getNumberOfDays());
        if(currentDate.equals(LocalDate.of(ourSeasonGame.getYear(),9,29))){
            //Decide how to move to next season
        }
        else {
            ourSeasonGame.setCurrentSimulation(ourSeasonGame.getAdvanceTime());
        }
    }
}
