package dhl.simulationStateMachine.States;

import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.GameState;
import dhl.simulationStateMachine.SimulationContext;

import java.util.Scanner;

public class SimulateState implements GameState {

    GameContext ourGame;
    final int maxSeasons = 10;
    final int minSeasons = 1;
    int simulationSeasonsCount;

    public SimulateState(GameContext newGame) {
        ourGame =  newGame;
        simulationSeasonsCount = 0;
    }

    @Override
    public void stateEntryProcess() {
        Scanner sc = new Scanner(System.in);
        int seasonsNumber = 0;

        System.out.println("--------------------LETS SIMULATE------------------------");
        System.out.println(" Selected League: "+ ourGame.getInMemoryLeague().getLeagueName());
        System.out.println(" Selected Team:" + ourGame.getSelectedTeam().getTeamName());
        System.out.println("How many seasons you wana simulate? ");
        while(seasonsNumber < minSeasons || seasonsNumber > maxSeasons){
            seasonsNumber = sc.nextInt();
            if(seasonsNumber <= minSeasons || seasonsNumber >= maxSeasons){
                System.out.println("You can't simulate less than 1 season or more than 10 seasons");
                System.out.println("How many seasons you wana simulate or Enter Exit to quit ");
            }
        }
        simulationSeasonsCount = seasonsNumber;
    }

    @Override
    public void stateProcess() {
        SimulationContext simulationContextObject = new SimulationContext();
        for(int i=0; i< simulationSeasonsCount; i++){
            simulationContextObject.startSeasonSimulation(i+1);
        }
    }

    @Override
    public void stateExitProcess() {
        ourGame.setGameinProgress(false);
    }

}
