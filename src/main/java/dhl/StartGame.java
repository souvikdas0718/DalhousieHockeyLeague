package dhl;

import dhl.simulationStateMachine.GameContext;

public class StartGame {
    public static void main(String[] args) throws Exception {
        System.out.println(" Welcome to Dynasty Mode ");
        GameContext ourGame= new GameContext();

        while(ourGame.isGameinProgress()){
            ourGame.stateEntryProcess();
            ourGame.stateProcess();
            ourGame.stateExitProcess();

        }

        System.out.println("Thanks for Playing");

    }

}
