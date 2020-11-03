package dhl.businessLogic.simulationStateMachine.States;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.database.LeagueObjectModelDB;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.Interface.IGameState;
import dhl.businessLogic.simulationStateMachine.States.Interface.ILoadTeamStateLogic;

public class LoadTeamStateUI implements IGameState {

    GameContext ourGame;
    ILeagueObjectModel ourLeague;
    ITeam selectedTeam;
    ILeagueObjectModel newInMemoryLeague;
    IUserInputOutput userInputPutput = new UserInputOutput();

    public LoadTeamStateUI(GameContext newGame) {
        newInMemoryLeague = new LeagueObjectModel();
        ourGame = newGame;
    }

    @Override
    public void stateEntryProcess() {
        System.out.print("Enter LeagueName to load from DB: ");
        String leagueName = userInputPutput.getUserInput();

        while (leagueName.equals("")) {
            userInputPutput.printMessage("Looks like you didnt add any input please try again: ");
            leagueName = userInputPutput.getUserInput();
        }

        System.out.print("Enter Team Name:  ");
        String team = userInputPutput.getUserInput();

        while (team.equals("")) {
            userInputPutput.printMessage("Looks like you didnt add any input please try again: ");
            team = userInputPutput.getUserInput();
        }

        try {
            ILoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic(leagueName, team);
            ILeagueObjectModelDB databaseRefrenceOb = new LeagueObjectModelDB();

            objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, databaseRefrenceOb);
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
