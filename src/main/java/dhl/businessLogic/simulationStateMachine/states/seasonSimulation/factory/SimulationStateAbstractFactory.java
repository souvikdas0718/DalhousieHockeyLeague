package dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory;

import dhl.businessLogic.leagueModel.interfaceModel.IGenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;

public abstract class SimulationStateAbstractFactory {

    private static SimulationStateAbstractFactory uniqueInstance = null;

    protected SimulationStateAbstractFactory() {

    }

    public static SimulationStateAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new SeasonSimulationStateFactory();
        }
        return uniqueInstance;
    }


    public abstract ISimulationSeasonState getAdvanceTimeState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getAdvanceToNextSeasonState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getAgingState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getExecuteTradesState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getGeneratePlayoffScheduleState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getInitializeSeasonState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getInjuryCheckState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getPersistSameSeasonState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getPersistSeasonState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getSimulateGameState(SimulationContext simulationContext);

    public abstract ISimulationSeasonState getTrainingState(SimulationContext simulationContext);

    public abstract IGenerateDraftPlayers getGeneratePlayers();

    public abstract ISimulationSeasonState getPlayerDraftState(SimulationContext simulationContext);

}
