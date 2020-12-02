package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamStateLogic implements ICreateTeamStateLogic {
    Logger myLogger = LogManager.getLogger(CreateTeamStateLogic.class);
    IUserInputOutput userInputPutput = IUserInputOutput.getInstance();

    public ILeagueObjectModel saveleagueObject(GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelInput leagueObjectModelInput) throws IOException {
        ILeagueObjectModel leagueObjectModel = inMemoryLeague.saveLeagueObjectModel(leagueObjectModelInput.getSerializeLeagueObjectModel(), leagueObjectModelInput);
        myLogger.debug("Saved league object model in json file");
        ITeam team = leagueObjectModelInput.getNewlyCreatedTeam();
        ourGame.setSelectedTeam(findTeam(inMemoryLeague, team.getTeamName()));

        return leagueObjectModel;
    }

    public ITeam createNewTeamObject(List<IPlayer> freeAgents, ITeam team, String captain) {
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

    public IGeneralManager findGeneralManager(List<IGeneralManager> generalManagerArray, String generalManager) {
        for (IGeneralManager ourGeneralManager : generalManagerArray) {
            String ourGeneralManagerName = ourGeneralManager.getGeneralManagerName();
            if (ourGeneralManagerName.equals(generalManager)) {
                return ourGeneralManager;
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

    public ArrayList<IPlayer> validateInputFreeAgents(String inputfreeAgents, List<IPlayer> freeAgentsArray) {
        String[] arrFreeAgents = inputfreeAgents.split(",");
        ArrayList<IPlayer> selectedFreeAgents = new ArrayList<>();

        if (arrFreeAgents.length == 30) {
            for (int i = 0; i < arrFreeAgents.length; i++) {

                String freeAgentName = arrFreeAgents[i].trim();
                IPlayer foundFreeAgent = findFreeAgent(freeAgentsArray, freeAgentName);

                if (foundFreeAgent == null) {
                    selectedFreeAgents = null;
                    userInputPutput.printMessage("Free agent " + freeAgentName + " Doesn't Exist");

                    return null;
                } else {
                    selectedFreeAgents.add(foundFreeAgent);
                }
            }
        } else {
            userInputPutput.printMessage("Enter 30 Free Agents or enter Exit to Quit game");
        }
        return selectedFreeAgents;
    }
}
