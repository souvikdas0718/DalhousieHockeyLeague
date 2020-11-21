package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IImportStateLogic {
    ILeagueObjectModel importAndGetLeagueObject(String validFilePath) throws Exception;

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);

    boolean jsonSchemaValidation(String leagueModel,String schemaJson);
}
