package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;

public abstract class StatesAbstractFactory {
    private static StatesAbstractFactory uniqueInstance = null;

    protected StatesAbstractFactory() {

    }

    public static StatesAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new StatesFactory();
        }
        return uniqueInstance;
    }

    public abstract ICreateTeamStateLogic createCreateTeamStateLogic();

    public abstract IImportStateLogic createImportStateLogic();

    public abstract ILoadTeamStateLogic createLoadTeamStateLogic();

    public abstract IGameContext createGameContext();

    public abstract SimulationContext createSimulationContext(GameContext newGame);
}

