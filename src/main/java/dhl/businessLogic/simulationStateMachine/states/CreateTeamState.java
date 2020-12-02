package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamState implements IGameState {
    private final String jsonFilePath = "src/serializedJsonFiles/";
    Logger myLogger = LogManager.getLogger(CreateTeamStateLogic.class);

    StatesAbstractFactory factory;
    ICreateTeamStateLogic createTeamStateLogic;
    IUserInputOutput userInputPutput = IUserInputOutput.getInstance();
    private GameContext ourGame;
    private ILeagueObjectModel inMemoryLeague;
    private IConference selectedConference;
    private IDivision selectedDivision;
    private String selectedTeamName;
    private IGeneralManager selectedGeneralManager;
    private String selectedHeadCoach;
    private ICoach selectedCoach;
    private List<IPlayer> selectedFreeAgents;
    private String selectedCaptain;

    public CreateTeamState(GameContext newGame) {
        ourGame = newGame;
        selectedConference = null;
        selectedDivision = null;
        selectedTeamName = selectedHeadCoach = null;
        selectedGeneralManager = null;
        selectedCoach = new Coach();
        factory = StatesAbstractFactory.instance();
        createTeamStateLogic = factory.createCreateTeamStateLogic();
    }

    public void stateEntryProcess() {
        inMemoryLeague = ourGame.getInMemoryLeague();

        userInputPutput.printMessage("------------------------LETS CREATE NEW TEAM---------------------");
        selectConference();
        selectDivision();

        userInputPutput.printMessage("------------------------LETS ADD NEW TEAM NOW---------------------");
        selectTeam();
        selectGeneralManager();
        selectTeamHeadCoach();
        selectFreeAgents();
    }

    public void selectConference() {
        List<IConference> conferencesArray = inMemoryLeague.getConferences();
        userInputPutput.printMessage("Select the Conference");

        for (int i = 0; i < conferencesArray.size(); i++) {
            Integer serialNumber = i + 1;
            userInputPutput.printMessage(serialNumber + ".  " + conferencesArray.get(i).getConferenceName());
        }

        String conference = userInputPutput.getUserInput();

        while (selectedConference == null) {
            selectedConference = createTeamStateLogic.findConference(conferencesArray, conference);
            if (selectedConference != null) {
                userInputPutput.printMessage("Confrence Found: " + selectedConference.getConferenceName());
                break;
            } else {
                userInputPutput.printMessage("Confrence Dont Exist");
                userInputPutput.printMessage("Enter the Conference Name Again or enter Exit to Quit game");
                conference = userInputPutput.getUserInput();
            }
        }
        if (conference.equals("Exit")) {
            System.exit(0);
        }
    }

    public void selectDivision() {
        List<IDivision> divisionArrayList = selectedConference.getDivisions();
        userInputPutput.printMessage("Select the Division");

        for (int i = 0; i < divisionArrayList.size(); i++) {
            Integer serialNumber = i + 1;
            userInputPutput.printMessage(serialNumber + ".  " + divisionArrayList.get(i).getDivisionName());
        }
        String division = userInputPutput.getUserInput();

        while (selectedDivision == null) {
            selectedDivision = createTeamStateLogic.findDivision(divisionArrayList, division);
            if (selectedDivision != null) {
                userInputPutput.printMessage("Division Found: " + selectedDivision.getDivisionName());
                break;
            } else {
                userInputPutput.printMessage("Division Dont Exist");
                userInputPutput.printMessage("Enter the Division Name Again or enter Exit to Quit game");
                division = userInputPutput.getUserInput();
            }
        }
        if (division.equals("Exit")) {
            System.exit(0);
        }
    }

    private void selectTeam() {
        String userInput;
        userInputPutput.printMessage("Enter New Team Name: ");
        userInput = userInputPutput.getUserInput();

        while (selectedTeamName == null) {
            if (userInput.equals("")) {
                userInputPutput.printMessage("invalid team name, Enter name again or enter Exit to exit ");
                userInput = userInputPutput.getUserInput();
            } else if (userInput.equals("Exit")) {
                System.exit(0);
            } else {
                selectedTeamName = userInput;
            }
        }
    }

    private void selectGeneralManager() {
        userInputPutput.printMessage("Select Team's General Manager Name: ");
        List<IGeneralManager> generalManagerArray = inMemoryLeague.getGeneralManagers();

        generalManagerArray.forEach((generalManager) -> {
            userInputPutput.printMessage(generalManager.getGeneralManagerName());
        });

        String generalManager = userInputPutput.getUserInput();

        while (selectedGeneralManager == null) {
            selectedGeneralManager = createTeamStateLogic.findGeneralManager(generalManagerArray, generalManager);
            if (selectedGeneralManager == null) {
                userInputPutput.printMessage("General Manager Doesn't Exist");
                userInputPutput.printMessage("Enter the General Manager Name Again or enter Exit to Quit game");
                generalManager = userInputPutput.getUserInput();
            }
        }
        if (generalManager.equals("Exit")) {
            System.exit(0);
        }
    }

    private void selectTeamHeadCoach() {
        userInputPutput.printMessage("Select Team's Head Coach Name: ");
        List<ICoach> coachArray = inMemoryLeague.getCoaches();

        userInputPutput.printMessage("-----------------------------------------------------------------------------");
        String coachListHeader = String.format("%20s %10s %10s %10s %10s", "Name", "Checking", "Saving", "Shooting", "Skating");
        userInputPutput.printMessage(coachListHeader);

        userInputPutput.printMessage("-----------------------------------------------------------------------------");

        coachArray.forEach((selectedCoach) -> {
            String formattedCoachList = String.format("%20s %10s %10s %10s %10s", selectedCoach.getCoachName(), Double.toString(selectedCoach.getChecking()), Double.toString(selectedCoach.getSaving()), Double.toString(selectedCoach.getShooting()), Double.toString(selectedCoach.getSkating()));
            userInputPutput.printMessage(formattedCoachList);

        });
        userInputPutput.printMessage("-----------------------------------------------------------------------------");

        String headCoach = userInputPutput.getUserInput();

        while (selectedHeadCoach == null) {
            selectedHeadCoach = createTeamStateLogic.findCoach(coachArray, headCoach);
            if (selectedHeadCoach == null) {
                userInputPutput.printMessage("Coach Doesn't Exist");
                userInputPutput.printMessage("Enter the Coach Name Again or enter Exit to Quit game");
                headCoach = userInputPutput.getUserInput();
            }
        }
        if (headCoach.equals("Exit")) {
            System.exit(0);
        }
        for (int i = 0; i < coachArray.size(); i++) {
            if (coachArray.get(i).getCoachName().equals(headCoach)) {
                selectedCoach = coachArray.get(i);
            }
        }
    }

    private void selectFreeAgents() {
        List<IPlayer> freeAgentsArray = inMemoryLeague.getFreeAgents();

        userInputPutput.printMessage("Select the Players from free Agents list (Input multiple names separated by a comma):");
        userInputPutput.printMessage("-----------------------------------------------------------------------------------------------------------------");
        String freeAgentListHeader = String.format("%20s %20s %10s %10s %10s %10s %10s %10s", "Name", "Position", "Age", "Checking", "Saving", "Shooting", "Skating", "Strength");
        userInputPutput.printMessage(freeAgentListHeader);
        userInputPutput.printMessage("-----------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < freeAgentsArray.size(); i++) {
            IPlayer player = new Player(freeAgentsArray.get(i).getPlayerName(), freeAgentsArray.get(i).getPosition(), freeAgentsArray.get(i).getPlayerStats());
            Double playerStrength = player.getPlayerStrength();

            String formattedFreeAgentList = String.format("%20s %20s %10d %10d %10d %10d %10d %10s", freeAgentsArray.get(i).getPlayerName(), freeAgentsArray.get(i).getPosition(), freeAgentsArray.get(i).getPlayerStats().getAge(), freeAgentsArray.get(i).getPlayerStats().getChecking(), freeAgentsArray.get(i).getPlayerStats().getSaving(), freeAgentsArray.get(i).getPlayerStats().getShooting(), freeAgentsArray.get(i).getPlayerStats().getSkating(), Double.toString(playerStrength));
            userInputPutput.printMessage(formattedFreeAgentList);
        }
        userInputPutput.printMessage("-----------------------------------------------------------------------------------------------------------------");

        userInputPutput.printMessage("Select 16 forwards, 10 defense and 4 Goalies");
        String inputfreeAgents = userInputPutput.getUserInput();

        while (selectedFreeAgents == null) {
            selectedFreeAgents = createTeamStateLogic.validateInputFreeAgents(inputfreeAgents, freeAgentsArray);

            ITeam team = new Team(selectedTeamName, selectedGeneralManager, selectedCoach, selectedFreeAgents);
            if (team.checkTeamPlayersCount() == false) {
                selectedFreeAgents = null;
                userInputPutput.printMessage("A team must have 16 forwards, 10 defense and 4 Goalies");
                inputfreeAgents = userInputPutput.getUserInput();
            } else {
                userInputPutput.printMessage("Choose a captain for this team from the selected players");
                selectedCaptain = userInputPutput.getUserInput();
            }

            if (inputfreeAgents.equals("Exit")) {
                System.exit(0);
            }
        }
    }

    public void stateProcess() {
        try {
            userInputPutput.printMessage("Adding Team " + selectedTeamName + " to the DB");
            myLogger.info("Adding Team " + selectedTeamName + " to the DB");

            ITeam teamWithoutPlayers = new Team(selectedTeamName, selectedGeneralManager, selectedCoach, new ArrayList<>());
            ITeam newlyCreatedTeam = createTeamStateLogic.createNewTeamObject(selectedFreeAgents, teamWithoutPlayers, selectedCaptain);
            ILeagueObjectModelValidation leagueObjectModelValidation = new LeagueObjectModelValidation();
            ISerializeLeagueObjectModel serializeLeagueObjectModel = new SerializeLeagueObjectModel(jsonFilePath);

            ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput(inMemoryLeague.getLeagueName(), selectedConference.getConferenceName(), selectedDivision.getDivisionName(), newlyCreatedTeam, leagueObjectModelValidation, serializeLeagueObjectModel);
            createTeamStateLogic.saveleagueObject(ourGame, inMemoryLeague, leagueObjectModelInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stateExitProcess() {
        if (ourGame.isGameInProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }
}
