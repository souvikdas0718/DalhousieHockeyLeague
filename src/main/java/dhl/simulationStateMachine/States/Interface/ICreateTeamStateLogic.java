package dhl.simulationStateMachine.States.Interface;

import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModel.interfaceModel.*;
import dhl.simulationStateMachine.GameContext;

import java.util.ArrayList;

public interface ICreateTeamStateLogic {
    public ILeagueObjectModel saveleagueObject(String leagueName, String conferenceName, String divisionName, String teamName,
                                               String generalManager, ArrayList<IFreeAgent> freeAgents, ICoach coach,
                                               GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelData leagueObjectModelData) throws Exception;
    public ITeam createNewTeamObject(ArrayList<IFreeAgent> freeAgents, String teamName,
                                     String generalManager, ICoach coach) throws Exception;
    public IConference findConference(ArrayList<IConference> confrenceArray, String conferenceName );
    public IDivision findDivision(ArrayList<IDivision> divisionArrayList , String divisionName);
    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);
    public IFreeAgent findFreeAgent(ArrayList<IFreeAgent> freeAgentArrayList, String freeAgentName);
    public String findGeneralManager(ArrayList<IGeneralManager> generalManagerArray, String generalManager );
    public String findCoach(ArrayList<ICoach> coachArray, String coachName );
}
