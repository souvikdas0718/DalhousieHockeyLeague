package dhl.businessLogic.simulationStateMachine.States;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.Interface.IGameState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

import java.util.Scanner;

public class SimulateState implements IGameState {

    GameContext ourGame;
    final int maxSeasons = 10;
    final int minSeasons = 1;
    int simulationSeasonsCount;
    IUserInputOutput userInputPutput;

    public SimulateState(GameContext newGame) {
        ourGame = newGame;
        simulationSeasonsCount = 0;
        userInputPutput = new UserInputOutput();
    }

    @Override
    public void stateEntryProcess() {
        int seasonsNumber = 0;

        userInputPutput.printMessage("--------------------LETS SIMULATE------------------------");
        userInputPutput.printMessage(" Selected League: " + ourGame.getInMemoryLeague().getLeagueName());
        userInputPutput.printMessage(" Selected Team:" + ourGame.getSelectedTeam().getTeamName());
        userInputPutput.printMessage("How many seasons you wana simulate? ");

        while (seasonsNumber < minSeasons || seasonsNumber > maxSeasons) {
            seasonsNumber = Integer.parseInt(userInputPutput.getUserInput());
            if (seasonsNumber <= minSeasons || seasonsNumber >= maxSeasons) {
                userInputPutput.printMessage("You can't simulate less than 1 season or more than 10 seasons");
                userInputPutput.printMessage("How many seasons you wana simulate or Enter Exit to quit ");
            }
        }
        simulationSeasonsCount = seasonsNumber;
    }

    @Override
    public void stateProcess() {
        SimulationContext simulationContextObject = new SimulationContext(ourGame);
        simulationContextObject.setGameConfig(ourGame.getGameConfig());
        simulationContextObject.setInMemoryLeague(ourGame.getInMemoryLeague());

        for (int i = 0; i < simulationSeasonsCount; i++) {
            simulationContextObject.seasonStateEntryProcess();
            simulationContextObject.seasonStateProcess();
            simulationContextObject.seasonStateExitProcess();
        }
    }

    @Override
    public void stateExitProcess() {
        ourGame.setGameInProgress(false);
    }

}
