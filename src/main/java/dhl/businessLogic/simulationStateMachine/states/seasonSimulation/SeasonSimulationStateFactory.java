package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class SeasonSimulationStateFactory extends SimulationStateAbstractFactory {
    @Override
    public ISimulationSeasonState getAdvanceTimeState(SimulationContext simulationContext) {
        return new AdvanceTimeState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getAdvanceToNextSeasonState(SimulationContext simulationContext) {
        return null;
    }

    @Override
    public ISimulationSeasonState getAgingState(SimulationContext simulationContext) {
        return new AgingState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getExecuteTradesState(SimulationContext simulationContext) {
        return new ExecuteTradesState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getGeneratePlayoffScheduleState(SimulationContext simulationContext) {
        return new GeneratePlayOffScheduleState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getInitializeSeasonState(SimulationContext simulationContext) {
        return new InitializeSeasonState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getInjuryCheckState(SimulationContext simulationContext) {
        return new InjuryCheckState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getPersistSameSeasonState(SimulationContext simulationContext) {
        return new PersistSameSeasonState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getPersistSeasonState(SimulationContext simulationContext) {
        return new PersistSeasonState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getSimulateGameState(SimulationContext simulationContext) {
        return new SimulateGameState(simulationContext);
    }

    @Override
    public ISimulationSeasonState getTrainingState(SimulationContext simulationContext) {
        return new TrainingState(simulationContext);
    }
}
