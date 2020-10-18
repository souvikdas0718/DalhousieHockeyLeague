package dhl.simulationStateMachine;

import dhl.importJson.Interface.IGameConfig;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.States.SeasonSimulationState;

public class SimulationContext {

    ISimulationSeasonState seasonSimulationState;
    ISimulationSeasonState currentSimulation;
    IGameConfig gameConfig;

    public SimulationContext(){
        seasonSimulationState = new SeasonSimulationState(this);
        currentSimulation = seasonSimulationState;
    }

    public void startSeasonSimulation(int seasonNumber){
        currentSimulation.startSeasonSimulation(seasonNumber);
    }

    public IGameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(IGameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }
}
