package dhl.simulationStateMachine.States.Interface;

import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.GameContext;

import java.util.ArrayList;

public interface ILoadTeamStateLogic {
    public Boolean findTeamOfLeagueInDatabase(String leagueName, String team, ILeagueObjectModel newInMemoryLeague, GameContext ourGame, ILeagueObjectModelData databaseRefrenceOb) throws Exception;
    public IConference findConference(ArrayList<IConference> confrenceArray, String conferenceName );
    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
}
