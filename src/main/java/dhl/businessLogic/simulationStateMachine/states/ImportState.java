package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.traning.Training;
import dhl.inputOutput.importJson.JsonFilePath;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ImportState implements IGameState {
    private static final Logger logger = LogManager.getLogger(Training.class);
    String validFilePath;
    ILeagueObjectModel newInMemoryLeague;
    int option = -1;
    GameContext ourGame;
    IUserInputOutput userInputPutput = IUserInputOutput.getInstance();

    public ImportState(GameContext newGame) {
        ourGame = newGame;
        validFilePath = null;
        newInMemoryLeague = new LeagueObjectModel();
    }

    public void stateEntryProcess() {
        while (option == -1 || option > 3) {
            userInputPutput.printMessage("Please Enter one option");
            userInputPutput.printMessage("1 for Loading JSON");
            userInputPutput.printMessage("2 for Loading Existing Team from DB");
            userInputPutput.printMessage("0 To Exit");

            try {
                option = Integer.parseInt(userInputPutput.getUserInput());
            } catch (NumberFormatException exception) {
                userInputPutput.printMessage("This is not a Correct Option");
            }
        }
        switch (option) {
            case 0:
                userInputPutput.printMessage("Case:0");
                System.exit(0);
            case 1:
                userInputPutput.printMessage("case :1");
                validFilePath = new JsonFilePath().getFilePath();
                break;
            case 2:
                userInputPutput.printMessage("===========LETS LOAD TEAM FROM DB THEN===========");
                break;
        }
    }

    public void stateProcess() {
        if (validFilePath != null) {
            try {
                IImportStateLogic objImportStateLogic = new ImportStateLogic();
                newInMemoryLeague = objImportStateLogic.importAndGetLeagueObject(validFilePath);
                if (newInMemoryLeague == null) {
                    userInputPutput.printMessage("Imported JSON values are incorrect. Please correct and retry");
                    ourGame.setGameInProgress(false);
                } else {
                    userInputPutput.printMessage(newInMemoryLeague.getLeagueName() + "  Imported from the Json");
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                ourGame.setGameInProgress(false);
            }
        }
    }

    public void stateExitProcess() {
        if (ourGame.isGameInProgress()) {
            ourGame.setInMemoryLeague(newInMemoryLeague);
            ourGame.setGameConfig(newInMemoryLeague.getGameConfig());
            if (option == 1) {
                ourGame.setGameState(ourGame.getCreateTeamState());
            } else if (option == 2) {
                ourGame.setGameState(ourGame.getLoadTeamState());
            }
        }
    }
}
