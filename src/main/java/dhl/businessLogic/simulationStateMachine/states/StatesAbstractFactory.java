package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.importJson.ImportJsonFactory;

public abstract class StatesAbstractFactory {
    private static StatesAbstractFactory uniqueInstance = null;

    protected StatesAbstractFactory(){

    }

    public static StatesAbstractFactory instance(){
        if (null == uniqueInstance){
            uniqueInstance = new StatesFactory();
        }
        return uniqueInstance;
    }

    public abstract IGameState createCreateTeamState(GameContext newGame);
    public abstract ICreateTeamStateLogic createCreateTeamStateLogic();
    public abstract IGameState createImportState(GameContext newGame);
    public abstract IImportStateLogic createImportStateLogic();
    public abstract IGameState createLoadTeamState(GameContext newGame);
    public abstract ILoadTeamStateLogic createLoadTeamStateLogic();
    public abstract IGameState createSimulateState(GameContext newGame);
}

