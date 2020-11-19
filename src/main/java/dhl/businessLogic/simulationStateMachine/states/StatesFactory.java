package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;

public class StatesFactory implements StatesAbstractFactory {

    public IGameState createCreateTeamState(GameContext newGame) {
        return new CreateTeamState(newGame);
    }

    public ICreateTeamStateLogic createCreateTeamStateLogic() {
        return new CreateTeamStateLogic();
    }

    public IGameState createImportState(GameContext newGame) {
        return new ImportState(newGame);
    }

    public IImportStateLogic createImportStateLogic() {
        return new ImportStateLogic();
    }

    public IGameState createLoadTeamState(GameContext newGame) {
        return new LoadTeamState(newGame);
    }

    public ILoadTeamStateLogic createLoadTeamStateLogic() {
        return new LoadTeamStateLogic();
    }

    public IGameState createSimulateState(GameContext newGame) {
        return new SimulateState(newGame);
    }
}
