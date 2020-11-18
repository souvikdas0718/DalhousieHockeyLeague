package dhl.businessLogic.simulationStateMachine.States;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.Interface.IGameState;
import dhl.businessLogic.simulationStateMachine.States.Interface.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.States.Interface.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.States.Interface.ILoadTeamStateLogic;

import java.util.ArrayList;
import java.util.List;

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
