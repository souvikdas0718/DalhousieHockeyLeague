package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

public class Training implements ISimulationSeasonState {

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
        HashMap agingConfig=gameConfig.getHashMap("training");
        int daysUntilStatIncreaseCheck=(int)(long)agingConfig.get("daysUntilStatIncreaseCheck");
        if(daysUntilStatIncreaseCheck==simulationContext.getDaysSinceLastTraining()){
            ITraining training = new Training(simulationContext.getInjurySystem());
            training.statIncrease(simulationContext.getInMemoryLeague());
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
