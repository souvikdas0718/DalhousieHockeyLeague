package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.Training;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.util.HashMap;

public class TrainingState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    public TrainingState(SimulationContext simulationContext) {
        this.simulationContext=simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess()  {
        simulationContext.setDaysSinceLastTraining(simulationContext.getDaysSinceLastTraining()+1);
        IGameConfig gameConfig = simulationContext.getGameConfig();
        int daysUntilStatIncreaseCheck=Integer.parseInt(gameConfig.getValueFromOurObject( gameConfig.getTrading(), gameConfig.getDaysUntilStatIncreaseCheck()));
        if(daysUntilStatIncreaseCheck==simulationContext.getDaysSinceLastTraining()){
            ITraining training = new Training(simulationContext.getInjurySystem());
            training.updatePlayerStats(simulationContext.getInMemoryLeague());
            simulationContext.setDaysSinceLastTraining(0);
        }
    }

    @Override
    public void seasonStateExitProcess() {
//    TODO Add call to switch to "Simulate state " or "Agein" here based on below conditions"
        // any unplayed game scheduled
        // yes then simulate game
        // no then check trade deadline
        // no execute trade
        // yes aging
    }
}
