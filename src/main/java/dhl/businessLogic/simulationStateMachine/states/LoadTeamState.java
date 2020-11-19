package dhl.businessLogic.simulationStateMachine.states;

import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.importJson.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IDeserializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;

public class LoadTeamState implements IGameState {

    GameContext ourGame;
    ILeagueObjectModel newInMemoryLeague;
    IUserInputOutput userInputPutput;

    public LoadTeamState(GameContext newGame) {
        newInMemoryLeague = new LeagueObjectModel();
        userInputPutput = new UserInputOutput();
        ourGame = newGame;
    }

    @Override
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
            IDeserializeLeagueObjectModel deserializeLeagueObjectModel = new DeserializeLeagueObjectModel();

            objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);
        } catch (Exception e) {
            userInputPutput.printMessage(e.getMessage());
            ourGame.setGameInProgress(false);
        }
        ;
    }

    @Override
    public void stateProcess() {
        if (ourGame.isGameInProgress()) {
            userInputPutput.printMessage(ourGame.getSelectedTeam().getTeamName() + "  Team Selected");
        }
    }

    @Override
    public void stateExitProcess() {
        if (ourGame.isGameInProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }
}
