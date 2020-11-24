package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.inputOutput.importJson.JsonFilePath;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;

public class ImportState implements IGameState {

    String validFilePath;
    ILeagueObjectModel newInMemoryLeague;
    int option = -1;
    GameContext ourGame;
    IUserInputOutput userInputPutput = new UserInputOutput();

    public ImportState(GameContext newGame) {
        ourGame = newGame;
        validFilePath = null;
        newInMemoryLeague = new LeagueObjectModel();
    }

    @Override
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

    @Override
    public void stateProcess() {
        if (validFilePath != null) {
            try {
                IImportStateLogic objImportStateLogic = new ImportStateLogic();
                newInMemoryLeague = objImportStateLogic.importAndGetLeagueObject(validFilePath);

                userInputPutput.printMessage(newInMemoryLeague.getLeagueName() + "  Imported from the Json");
            } catch (Exception e) {
                userInputPutput.printMessage(e.getMessage());
                ourGame.setGameInProgress(false);
            }
        }
    }

    @Override
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
