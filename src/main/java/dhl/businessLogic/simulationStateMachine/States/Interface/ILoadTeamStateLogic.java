package dhl.businessLogic.simulationStateMachine.States.Interface;

import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;

public interface ILoadTeamStateLogic {
    Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague, GameContext ourGame, IDeserializeLeagueObjectModel deserializeLeagueObjectModel) throws Exception;

}
