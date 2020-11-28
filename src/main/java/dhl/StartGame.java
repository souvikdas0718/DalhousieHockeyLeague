package dhl;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;

public class StartGame {

    public static void main(String[] args) throws Exception {
        IUserInputOutput ioObject = new UserInputOutput();
        ioObject.printMessage(" Welcome to Dynasty Mode ");
        StatesAbstractFactory statesFactory = StatesAbstractFactory.instance();
        IGameContext ourGame = statesFactory.createGameContext();


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
