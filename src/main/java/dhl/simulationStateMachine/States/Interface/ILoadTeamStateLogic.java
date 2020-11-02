package dhl.simulationStateMachine.States.Interface;

import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.simulationStateMachine.GameContext;

public interface ILoadTeamStateLogic {
    Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague, GameContext ourGame, ILeagueObjectModelDB databaseRefrenceOb) throws Exception;

}
