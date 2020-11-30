package dhl.businessLogic.simulationStateMachine.factory;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public abstract class ContextAbstractFactory {
    private static ContextAbstractFactory uniqueInstance = null;

    protected ContextAbstractFactory() {

    }

    public static ContextAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new ContextFactory();
        }
        return uniqueInstance;
    }

    public abstract GameContext createGameContext();

    public abstract SimulationContext createSimulationContext();

    public abstract SimulationContext createSimulationContextWithGameContext(GameContext gameContext);

}
