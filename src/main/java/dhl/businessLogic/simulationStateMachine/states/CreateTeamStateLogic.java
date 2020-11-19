package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import org.apache.logging.log4j.*;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamStateLogic implements ICreateTeamStateLogic {
    Logger myLogger = LogManager.getLogger(CreateTeamStateLogic.class);

    public ILeagueObjectModel saveleagueObject(GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelInput leagueObjectModelInput) throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();

        try {
            leagueObjectModel = inMemoryLeague.saveLeagueObjectModel(leagueObjectModelInput.getserializeLeagueObjectModel(), leagueObjectModelInput);
            ITeam team = leagueObjectModelInput.getNewlyCreatedTeam();
            ourGame.setSelectedTeam(findTeam(inMemoryLeague, team.getTeamName()));
        } catch (Exception  e) {
            myLogger.log(myLogger.getLevel(),e.getMessage());
            System.out.println(e.getMessage());
            ourGame.setGameInProgress(false);
        }

        return leagueObjectModel;
    }

    public ITeam createNewTeamObject(List<IPlayer> freeAgents, ITeam team, String captain) throws Exception {
        List<IPlayer> players = new ArrayList<>();

        freeAgents.forEach((freeAgent) -> {
            Boolean isCaptain = false;
            if (freeAgent.getPlayerName().equals(captain)) {
                isCaptain = true;
            }
            IPlayer player = new Player(freeAgent.getPlayerName(), freeAgent.getPosition(), isCaptain, freeAgent.getPlayerStats());
            players.add(player);
        });

        ITeam newlyCreatedTeam = new Team(team.getTeamName(), team.getGeneralManager(), team.getHeadCoach(), players);

        return newlyCreatedTeam;
    }

    public IConference findConference(List<IConference> confrenceArray, String conferenceName) {
        for (int i = 0; i < confrenceArray.size(); i++) {
            IConference ourConference = confrenceArray.get(i);
            if (ourConference.getConferenceName().equals(conferenceName)) {
                return ourConference;
            }
        }
        return null;
    }

    public IDivision findDivision(List<IDivision> divisionArrayList, String divisionName) {
        for (int i = 0; i < divisionArrayList.size(); i++) {
            IDivision ourDivision = divisionArrayList.get(i);
            if (ourDivision.getDivisionName().equals(divisionName)) {
                return ourDivision;
            }
        }
        return null;
    }

    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName) {
        ITeam teamObject = null;

        for (IConference conference : inMemoryLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (team.getTeamName().equals(teamName)) {
                        teamObject = team;
                    }
                }
            }
        }
        return teamObject;
    }

    public IPlayer findFreeAgent(List<IPlayer> freeAgentArrayList, String freeAgentName) {
        for (IPlayer ourFreeAgent : freeAgentArrayList) {
            String name = ourFreeAgent.getPlayerName();
            if (name.equals(freeAgentName)) {
                return ourFreeAgent;
            }
        }
        return null;
    }

    public String findGeneralManager(List<IGeneralManager> generalManagerArray, String generalManager) {
        for (IGeneralManager ourGeneralManager : generalManagerArray) {
            String ourGeneralManagerName = ourGeneralManager.getGeneralManagerName();
            if (ourGeneralManagerName.equals(generalManager)) {
                return generalManager;
            }
        }
        return null;
    }

    public String findCoach(List<ICoach> coachArray, String coachName) {
        for (ICoach ourCoach : coachArray) {
            String coachesName = ourCoach.getCoachName();
            if (coachesName.equals(coachName)) {
                return coachName;
            }
        }
        return null;
    }

    public ArrayList<IPlayer> validateInputFreeAgents(String inputfreeAgents, List<IPlayer> freeAgentsArray) throws Exception {
        String[] arrFreeAgents = inputfreeAgents.split(",");
        ArrayList<IPlayer> selectedFreeAgents = new ArrayList<>();

        if (arrFreeAgents.length == 20) {
            for (int i = 0; i < arrFreeAgents.length; i++) {

                String freeAgentName = arrFreeAgents[i].trim();
                IPlayer foundFreeAgent = findFreeAgent(freeAgentsArray, freeAgentName);

                if (foundFreeAgent == null) {
                    selectedFreeAgents = null;
                    throw new Exception("Free agent " + freeAgentName + " Doesn't Exist");
                } else {
                    selectedFreeAgents.add(foundFreeAgent);
                }
            }
        } else {
            myLogger.log(myLogger.getLevel(),"Enter 20 Free Agents or enter Exit to Quit game");

        }
        return selectedFreeAgents;
    }
}
