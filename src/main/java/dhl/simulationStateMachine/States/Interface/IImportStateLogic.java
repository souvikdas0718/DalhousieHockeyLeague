package dhl.simulationStateMachine.States.Interface;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface IImportStateLogic {
    public ILeagueObjectModel importAndGetLeagueObject(String validFilePath, IGameConfig gameConfig, ILeagueObjectModel newInMemoryLeague) throws Exception;
    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
}
