package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IImportStateLogic {
    ILeagueObjectModel importAndGetLeagueObject(String validFilePath, IGameConfig gameConfig, ILeagueObjectModel newInMemoryLeague) throws Exception;

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
}
