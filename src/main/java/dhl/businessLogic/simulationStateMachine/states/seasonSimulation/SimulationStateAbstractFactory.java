package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public interface SimulationStateAbstractFactory {


    public ISimulationSeasonState getAdvanceTimeState(SimulationContext simulationContext);
    public ISimulationSeasonState getAdvanceToNextSeasonState(SimulationContext simulationContext);
    public ISimulationSeasonState getAgingState(SimulationContext simulationContext);
    public ISimulationSeasonState getExecuteTradesState(SimulationContext simulationContext);
    public ISimulationSeasonState getGeneratePlayoffScheduleState(SimulationContext simulationContext);
    public ISimulationSeasonState getInitializeSeasonState(SimulationContext simulationContext);
    public ISimulationSeasonState getInjuryCheckState(SimulationContext simulationContext);
    public ISimulationSeasonState getPersistSameSeasonState(SimulationContext simulationContext);
    public ISimulationSeasonState getPersistSeasonState(SimulationContext simulationContext);
    public ISimulationSeasonState getSimulateGameState(SimulationContext simulationContext);
    public ISimulationSeasonState getTrainingState(SimulationContext simulationContext);
}
