package dhl;

import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;
import dhl.businessLogic.simulationStateMachine.GameContext;

public class StartGame {

    public static void main(String[] args) throws Exception {
        IUserInputOutput ioObject = new UserInputOutput();
        ioObject.printMessage(" Welcome to Dynasty Mode ");
        GameContext ourGame = new GameContext();

        while (ourGame.isGameInProgress()) {
            if (ourGame.isGameInProgress()){
                ourGame.stateEntryProcess();
            }
            if (ourGame.isGameInProgress()) {
                ourGame.stateProcess();
            }
            if (ourGame.isGameInProgress()) {
                ourGame.stateExitProcess();
            }
        }

        ioObject.printMessage("==============================GAME FINISHED==============================");
        ioObject.printMessage("Thanks for Playing");
    }


}
