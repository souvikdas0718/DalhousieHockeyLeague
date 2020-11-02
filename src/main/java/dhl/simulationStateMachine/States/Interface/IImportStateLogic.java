package dhl.simulationStateMachine.States.Interface;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface IImportStateLogic {
    ILeagueObjectModel importAndGetLeagueObject(String validFilePath, IGameConfig gameConfig, ILeagueObjectModel newInMemoryLeague) throws Exception;

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
}
