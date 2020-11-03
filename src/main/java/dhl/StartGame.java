package dhl;

import dhl.businessLogic.simulationStateMachine.GameContext;

public class StartGame {

    public static void main(String[] args) throws Exception {
        System.out.println(" Welcome to Dynasty Mode ");
        GameContext ourGame = new GameContext();

        while (ourGame.isGameInProgress()) {
            ourGame.stateEntryProcess();
            ourGame.stateProcess();
            ourGame.stateExitProcess();
        }

        System.out.println("==============================GAME FINISHED==============================");
        System.out.println("Thanks for Playing");
    }

}
