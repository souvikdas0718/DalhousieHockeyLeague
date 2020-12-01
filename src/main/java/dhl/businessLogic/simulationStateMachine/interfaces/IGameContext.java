package dhl.businessLogic.simulationStateMachine.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IGameContext {

    void setGameState(IGameState newState);

    void stateEntryProcess();

    void stateProcess() throws Exception;

    void stateExitProcess();

    ILeagueObjectModel getInMemoryLeague();

    void setInMemoryLeague(ILeagueObjectModel newInMemoryLeague);

    ITeam getSelectedTeam();

    void setSelectedTeam(ITeam selectedTeam);

    IGameState getSimulateState();

    IGameState getImportState();

    IGameState getLoadTeamState();

    IGameState getCreateTeamState();

    boolean isGameInProgress();

    void setGameInProgress(boolean gameStatus);

    IGameConfig getGameConfig();

    void setGameConfig(IGameConfig gameConfig);
}
