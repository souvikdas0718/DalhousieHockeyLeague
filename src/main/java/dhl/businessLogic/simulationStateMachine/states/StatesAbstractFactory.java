package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;

public interface StatesAbstractFactory {
    public IGameState createCreateTeamState(GameContext newGame);
    public ICreateTeamStateLogic createCreateTeamStateLogic();
    public IGameState createImportState(GameContext newGame);
    public IImportStateLogic createImportStateLogic();
    public IGameState createLoadTeamState(GameContext newGame);
    public ILoadTeamStateLogic createLoadTeamStateLogic();
    public IGameState createSimulateState(GameContext newGame);
}
