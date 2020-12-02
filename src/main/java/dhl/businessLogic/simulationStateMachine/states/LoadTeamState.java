package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;
import dhl.businessLogic.traning.Training;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadTeamState implements IGameState {
    private static final Logger logger = LogManager.getLogger(Training.class);
    GameContext ourGame;
    ILeagueObjectModel newInMemoryLeague;
    IUserInputOutput userInputPutput;

    public LoadTeamState(GameContext newGame) {
        newInMemoryLeague = new LeagueObjectModel();
        userInputPutput = IUserInputOutput.getInstance();
        ourGame = newGame;
    }

    public void stateEntryProcess() {
        userInputPutput.printMessage("Enter LeagueName to load from DB: ");
        String leagueName = userInputPutput.getUserInput();

        while (leagueName.equals("")) {
            userInputPutput.printMessage("Looks like you didnt add any input please try again: ");
            leagueName = userInputPutput.getUserInput();
        }

        userInputPutput.printMessage("Enter Team Name:  ");
        String team = userInputPutput.getUserInput();

        while (team.equals("")) {
            userInputPutput.printMessage("Looks like you didnt add any input please try again: ");
            team = userInputPutput.getUserInput();
        }

        try {
            ILoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic(leagueName, team);
            SerializeDeserializeAbstractFactory factory = SerializeDeserializeAbstractFactory.instance();
            IDeserializeLeagueObjectModel deserializeLeagueObjectModel = factory.createDeserializeLeagueObjectModel(leagueName);

            objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);
            logger.debug("Team loaded successfully");
        } catch (Exception e) {
            logger.error("Error while loading team: " + e.getMessage());
            ourGame.setGameInProgress(false);
        }
    }

    public void stateProcess() {
        if (ourGame.isGameInProgress()) {
            userInputPutput.printMessage(ourGame.getSelectedTeam().getTeamName() + "  Team Selected");
        }
    }

    public void stateExitProcess() {
        if (ourGame.isGameInProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }
}
