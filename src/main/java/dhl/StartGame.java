package dhl;

import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.trade.Scout;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartGame {

    private static final Logger logger = LogManager.getLogger(StartGame.class);

    public static void main(String[] args) throws Exception {
        IUserInputOutput ioObject = IUserInputOutput.getInstance();
        ioObject.printMessage(" Welcome to Dynasty Mode ");
        GameContext ourGame = new GameContext();

        logger.info("Starting Game");

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

        logger.info("Game finished");
        ioObject.printMessage("==============================GAME FINISHED==============================");
        ioObject.printMessage("Thanks for Playing");
    }


}
