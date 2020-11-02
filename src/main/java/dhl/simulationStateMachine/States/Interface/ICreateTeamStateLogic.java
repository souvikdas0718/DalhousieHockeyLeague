package dhl.simulationStateMachine.States.Interface;

import dhl.leagueModel.interfaceModel.*;
import dhl.simulationStateMachine.GameContext;

import java.util.ArrayList;
import java.util.List;

public interface ICreateTeamStateLogic {
    ILeagueObjectModel saveleagueObject(GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelInput leagueObjectModelInput) throws Exception;

    ITeam createNewTeamObject(List<IPlayer> freeAgents, ITeam team, String captain) throws Exception;

    IConference findConference(List<IConference> confrenceArray, String conferenceName);

    IDivision findDivision(List<IDivision> divisionArrayList, String divisionName);

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);

    IPlayer findFreeAgent(List<IPlayer> freeAgentArrayList, String freeAgentName);

    String findGeneralManager(List<IGeneralManager> generalManagerArray, String generalManager);

    String findCoach(List<ICoach> coachArray, String coachName);

    ArrayList<IPlayer> validateInputFreeAgents(String inputfreeAgents,
                                                      List<IPlayer> freeAgentsArray) throws Exception;
}
