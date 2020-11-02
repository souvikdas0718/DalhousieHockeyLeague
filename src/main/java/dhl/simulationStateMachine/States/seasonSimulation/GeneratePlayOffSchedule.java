package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.Interface.IStandings;
import dhl.simulationStateMachine.SimulationContext;

public class GeneratePlayOffSchedule implements ISimulationSeasonState {

    SimulationContext ourSeasonGame;
    IScheduler scheduler;

    public GeneratePlayOffSchedule(SimulationContext simulationContext) {
        this.ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // use NHL Stanley Cup rules to generate playoff rules
        ourSeasonGame.getRegularScheduler();
        scheduler.playOffs(scheduler.getGameStandings() , ourSeasonGame.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        // training
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getTraining());
    }
}
