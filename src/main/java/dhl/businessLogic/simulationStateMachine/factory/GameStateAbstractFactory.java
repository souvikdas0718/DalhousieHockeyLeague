package dhl.businessLogic.simulationStateMachine.factory;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GameStateAbstractFactory {

    private static GameStateAbstractFactory uniqueInstance = null;
    private static final Logger logger = LogManager.getLogger(GameStateAbstractFactory.class);

    protected GameStateAbstractFactory() {

    }

    public static GameStateAbstractFactory instance() {
        if (null == uniqueInstance) {
            logger.debug("GameStateAbstractFactory's instance created");
            uniqueInstance = new GameStateConcreteFactory();
        }
        logger.debug("GameStateAbstractFactory's instance returned");
        return uniqueInstance;
    }

    public abstract IGameState createImportState(GameContext newGame);

    public abstract IGameState createLoadTeamState(GameContext newGame);

    public abstract IGameState createSimulateState(GameContext newGame);

    public abstract IGameState createCreateTeamState(GameContext newGame);

}
