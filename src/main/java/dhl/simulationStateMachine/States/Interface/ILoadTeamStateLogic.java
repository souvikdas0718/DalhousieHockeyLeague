package dhl.simulationStateMachine.States.Interface;

import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.GameContext;

import java.util.ArrayList;

public interface ILoadTeamStateLogic {
    public Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague, GameContext ourGame, ILeagueObjectModelDB databaseRefrenceOb) throws Exception;

}
