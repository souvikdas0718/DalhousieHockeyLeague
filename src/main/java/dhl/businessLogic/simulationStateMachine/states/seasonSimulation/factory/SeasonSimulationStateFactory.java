package dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory;

import dhl.businessLogic.leagueModel.GenerateDraftPlayers;
import dhl.businessLogic.leagueModel.interfaceModel.IGenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.*;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;

public class SeasonSimulationStateFactory extends SimulationStateAbstractFactory {

    public ISimulationSeasonState getAdvanceTimeState(SimulationContext simulationContext) {
        return new AdvanceTimeState(simulationContext);
    }

    public ISimulationSeasonState getAdvanceToNextSeasonState(SimulationContext simulationContext) {
        return new AdvanceToNextSeasonState(simulationContext);
    }

    public ISimulationSeasonState getAgingState(SimulationContext simulationContext) {
        return new AgingState(simulationContext);
    }

    public ISimulationSeasonState getExecuteTradesState(SimulationContext simulationContext) {
        return new ExecuteTradesState(simulationContext);
    }

    public ISimulationSeasonState getGeneratePlayoffScheduleState(SimulationContext simulationContext) {
        return new GeneratePlayOffScheduleState(simulationContext);
    }

    public ISimulationSeasonState getInitializeSeasonState(SimulationContext simulationContext) {
        return new InitializeSeasonState(simulationContext);
    }

    public ISimulationSeasonState getInjuryCheckState(SimulationContext simulationContext) {
        return new InjuryCheckState(simulationContext);
    }

    public ISimulationSeasonState getPersistSameSeasonState(SimulationContext simulationContext) {
        return new PersistSameSeasonState(simulationContext);
    }

    public ISimulationSeasonState getPersistSeasonState(SimulationContext simulationContext) {
        return new PersistSeasonState(simulationContext);
    }

    public ISimulationSeasonState getSimulateGameState(SimulationContext simulationContext) {
        return new SimulateGameState(simulationContext);
    }

    public ISimulationSeasonState getTrainingState(SimulationContext simulationContext) {
        return new TrainingState(simulationContext);
    }

    public IGenerateDraftPlayers getGeneratePlayers() {
        return new GenerateDraftPlayers();
    }

    public ISimulationSeasonState getPlayerDraftState(SimulationContext simulationContext) {
        return new PlayerDraftState(simulationContext);
    }
}
