package dhl.businessLogic.simulationStateMachine.factory;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

public class ContextFactory extends ContextAbstractFactory {

    public GameContext createGameContext() {
        return new GameContext();
    }

    public SimulationContext createSimulationContext() {
        return new SimulationContext(createGameContext());
    }

    public SimulationContext createSimulationContextWithGameContext(GameContext gameContext) {
        return new SimulationContext(gameContext);
    }
}
