package dhl.businessLogic.simulationStateMachine.factory;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;

public abstract class GameStateAbstractFactory {

    private static GameStateAbstractFactory uniqueInstance = null;

    protected GameStateAbstractFactory() {

    }

    public static GameStateAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new GameStateConcreteFactory();
        }
        return uniqueInstance;
    }

    public abstract IGameState createImportState(GameContext newGame);
    public abstract IGameState createLoadTeamState(GameContext newGame);
    public abstract IGameState createSimulateState(GameContext newGame);
    public abstract IGameState createCreateTeamState(GameContext newGame);

}
