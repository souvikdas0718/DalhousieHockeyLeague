package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.io.IOException;

public interface IImportStateLogic {
    ILeagueObjectModel importAndGetLeagueObject(String validFilePath) throws IOException;

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);

    boolean jsonSchemaValidation(String leagueModel, String schemaJson);
}
