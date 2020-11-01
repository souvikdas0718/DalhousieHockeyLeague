package dhl.simulationStateMachine.States.Interface;

import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.interfaceModel.*;
import dhl.simulationStateMachine.GameContext;

import java.util.ArrayList;
import java.util.List;

public interface ICreateTeamStateLogic {
    public ILeagueObjectModel saveleagueObject(GameContext ourGame, ILeagueObjectModel inMemoryLeague,ILeagueObjectModelInput leagueObjectModelInput) throws Exception;
    public ITeam createNewTeamObject(List<IPlayer> freeAgents, ITeam team, String captain) throws Exception;
    public IConference findConference(List<IConference> confrenceArray, String conferenceName );
    public IDivision findDivision(List<IDivision> divisionArrayList , String divisionName);
    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
    public IPlayer findFreeAgent(List<IPlayer> freeAgentArrayList, String freeAgentName);
    public String findGeneralManager(List<IGeneralManager> generalManagerArray, String generalManager );
    public String findCoach(List<ICoach> coachArray, String coachName );
    public ArrayList<IPlayer> validateInputFreeAgents(String inputfreeAgents,
                                                         List<IPlayer> freeAgentsArray) throws Exception;
}
