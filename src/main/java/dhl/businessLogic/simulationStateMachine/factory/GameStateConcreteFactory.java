package dhl.businessLogic.simulationStateMachine.factory;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.CreateTeamState;
import dhl.businessLogic.simulationStateMachine.states.ImportState;
import dhl.businessLogic.simulationStateMachine.states.LoadTeamState;
import dhl.businessLogic.simulationStateMachine.states.SimulateState;

public class GameStateConcreteFactory extends GameStateAbstractFactory {

    public IGameState createImportState(GameContext newGame) {
        return new ImportState(newGame);
    }

    public IGameState createLoadTeamState(GameContext newGame) {
        return new LoadTeamState(newGame);
    }

    public IGameState createSimulateState(GameContext newGame) {
        return new SimulateState(newGame);
    }

    public IGameState createCreateTeamState(GameContext newGame) {
        return new CreateTeamState(newGame);
    }
}
