package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class SimulateState implements IGameState {
    final int maxSeasons = 10;
    final int minSeasons = 1;
    GameContext ourGame;
    int simulationSeasonsCount;
    IUserInputOutput userInputOutput;
    LeagueModelAbstractFactory leagueFactory;
    ImportJsonAbstractFactory importJsonFactory;

    public SimulateState(GameContext newGame) {
        ourGame = newGame;
        simulationSeasonsCount = 0;
        userInputOutput = IUserInputOutput.getInstance();
        leagueFactory = LeagueModelAbstractFactory.instance();
        importJsonFactory = ImportJsonAbstractFactory.instance();
    }

    @Override
    public void stateEntryProcess() {
        int seasonsNumber = 0;

        userInputOutput.printMessage("--------------------LETS SIMULATE------------------------");
        userInputOutput.printMessage(" Selected League: " + ourGame.getInMemoryLeague().getLeagueName());
        userInputOutput.printMessage(" Selected Team:" + ourGame.getSelectedTeam().getTeamName());
        userInputOutput.printMessage("How many seasons you wana simulate? ");

        while (seasonsNumber < minSeasons || seasonsNumber > maxSeasons) {
            seasonsNumber = Integer.parseInt(userInputOutput.getUserInput());
            if (seasonsNumber <= minSeasons || seasonsNumber >= maxSeasons) {
                userInputOutput.printMessage("You can't simulate less than 1 season or more than 10 seasons");
                userInputOutput.printMessage("How many seasons you wana simulate or Enter Exit to quit ");
            }
        }
        simulationSeasonsCount = seasonsNumber;
    }

    @Override
    public void stateProcess() {
        SimulationContext simulationContextObject = new SimulationContext(ourGame);
        simulationContextObject.setInMemoryLeague(ourGame.getInMemoryLeague());
        System.out.println(ourGame.getInMemoryLeague().getGameConfig());
        simulationContextObject.setYear(ourGame.getYear());
        simulationContextObject.setGameConfig(ourGame.getInMemoryLeague().getGameConfig());
        simulationContextObject.setInMemoryLeague(ourGame.getInMemoryLeague());
        userInputOutput.printMessage("Total simulation season count: " + simulationSeasonsCount);
        for (int i = 1; i <= simulationSeasonsCount; i++) {

            while (simulationContextObject.isSeasonInProgress()) {
                userInputOutput.printMessage("Season " + i + ": is in progress");
                simulationContextObject.seasonStateProcess();
                userInputOutput.printMessage("Season " + i + ": state process done");
                simulationContextObject.seasonStateExitProcess();
                userInputOutput.printMessage("Season " + i + ": exit process done");
            }
            ourGame.setYear(ourGame.getYear() + 1);
            simulationContextObject.setYear(ourGame.getYear() + 1);
        }
    }

    @Override
    public void stateExitProcess() {
        ourGame.setGameInProgress(false);
    }

}
