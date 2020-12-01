package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ICreateTeamStateLogic {
    ILeagueObjectModel saveleagueObject(GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelInput leagueObjectModelInput) throws IOException;

    ITeam createNewTeamObject(List<IPlayer> freeAgents, ITeam team, String captain);

    IConference findConference(List<IConference> confrenceArray, String conferenceName);

    IDivision findDivision(List<IDivision> divisionArrayList, String divisionName);

    ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName);

    IPlayer findFreeAgent(List<IPlayer> freeAgentArrayList, String freeAgentName);

    IGeneralManager findGeneralManager(List<IGeneralManager> generalManagerArray, String generalManager);

    String findCoach(List<ICoach> coachArray, String coachName);

    ArrayList<IPlayer> validateInputFreeAgents(String inputfreeAgents, List<IPlayer> freeAgentsArray);
}
