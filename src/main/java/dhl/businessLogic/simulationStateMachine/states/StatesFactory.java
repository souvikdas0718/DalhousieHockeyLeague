package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;

public class StatesFactory extends StatesAbstractFactory {

    public ICreateTeamStateLogic createCreateTeamStateLogic() {
        return new CreateTeamStateLogic();
    }

    public IImportStateLogic createImportStateLogic() {
        return new ImportStateLogic();
    }

    public ILoadTeamStateLogic createLoadTeamStateLogic() {
        return new LoadTeamStateLogic();
    }

    public IGameContext createGameContext() {
        return new GameContext();
    }

    public SimulationContext createSimulationContext(GameContext newGame) {
        return new SimulationContext(newGame);
    }
}
