package dhl.businessLogic.simulationStateMachine.States;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.Interface.IGameState;
import dhl.businessLogic.simulationStateMachine.States.Interface.ICreateTeamStateLogic;
import dhl.businessLogic.simulationStateMachine.States.Interface.IImportStateLogic;
import dhl.businessLogic.simulationStateMachine.States.Interface.ILoadTeamStateLogic;
import org.apache.logging.log4j.*;

import java.util.ArrayList;
import java.util.List;

public interface StatesAbstractFactory {
    public IGameState createCreateTeamState(GameContext newGame);
    public ICreateTeamStateLogic createCreateTeamStateLogic();
    public IGameState createImportState(GameContext newGame);
    public IImportStateLogic createImportStateLogic();
    public IGameState createLoadTeamState(GameContext newGame);
    public ILoadTeamStateLogic createLoadTeamStateLogic();
    public IGameState createSimulateState(GameContext newGame);
}
