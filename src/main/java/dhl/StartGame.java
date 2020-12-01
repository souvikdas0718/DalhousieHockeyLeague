package dhl;

import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartGame {

    private static final Logger logger = LogManager.getLogger(StartGame.class);

    public static void main(String[] args) throws Exception {
        IUserInputOutput ioObject = IUserInputOutput.getInstance();
        ioObject.printMessage("-------------------------Welcome to Dynasty Mode-------------------------");
        StatesAbstractFactory statesFactory = StatesAbstractFactory.instance();
        IGameContext ourGame = statesFactory.createGameContext();

        logger.info("Starting Game");

        while (ourGame.isGameInProgress()) {
            if (ourGame.isGameInProgress()) {
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
    }


}
