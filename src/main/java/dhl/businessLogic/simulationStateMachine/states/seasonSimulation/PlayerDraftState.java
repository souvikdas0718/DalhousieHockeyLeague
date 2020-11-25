package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.IGenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;

public class PlayerDraftState implements ISimulationSeasonState {
    IGenerateDraftPlayers generateDraftPlayers;
    SimulationContext simulationContext;
    SimulationStateAbstractFactory simulationFactory;

    public PlayerDraftState(){
        simulationFactory = SimulationStateAbstractFactory.instance();
        generateDraftPlayers = simulationFactory.getGeneratePlayers() ;
    }

    public void seasonStateEntryProcess() {

    }

    public void seasonStateProcess() {

    }

    public void seasonStateExitProcess() {

    }
}
