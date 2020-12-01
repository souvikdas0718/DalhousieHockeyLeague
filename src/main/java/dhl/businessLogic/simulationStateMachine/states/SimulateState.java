package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.gameSimulation.GameSimulationAbstractFactory;
import dhl.businessLogic.gameSimulation.IGameObserver;
import dhl.businessLogic.gameSimulation.ISubject;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.factory.ContextAbstractFactory;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimulateState implements IGameState {
    private static final int MAXSEASONS = 10;
    private static final int MINSEASONS = 1;
    private static final Logger logger = LogManager.getLogger(SimulateState.class);
    private static int SEASONNUMBER = 0;
    private static int SIMULATIONSEASONCOUNT = 0;
    GameContext ourGame;
    IUserInputOutput userInputOutput;
    LeagueModelAbstractFactory leagueFactory;
    ImportJsonAbstractFactory importJsonFactory;
    ContextAbstractFactory contextAbstractFactory;

    public SimulateState(GameContext newGame) {
        logger.info("Into Simulate game constructor, Initialising the GameContext values to the state variables");
        ourGame = newGame;
        userInputOutput = IUserInputOutput.getInstance();
        leagueFactory = LeagueModelAbstractFactory.instance();
        importJsonFactory = ImportJsonAbstractFactory.instance();
        contextAbstractFactory = ContextAbstractFactory.instance();
    }

    @Override
    public void stateEntryProcess() {
        userInputOutput.printMessage("--------------------LETS SIMULATE------------------------");
        userInputOutput.printMessage(" Selected League: " + ourGame.getInMemoryLeague().getLeagueName());
        userInputOutput.printMessage(" Selected Team:" + ourGame.getSelectedTeam().getTeamName());
        userInputOutput.printMessage("How many seasons you wana simulate? ");

        while (SEASONNUMBER < MINSEASONS || SEASONNUMBER > MAXSEASONS) {
            SEASONNUMBER = Integer.parseInt(userInputOutput.getUserInput());
            if (SEASONNUMBER < MINSEASONS || SEASONNUMBER > MAXSEASONS) {
                userInputOutput.printMessage("You can't simulate less than 1 season or more than 10 seasons");
                userInputOutput.printMessage("How many seasons you wana simulate or Enter Exit to quit ");
            }
        }
        SIMULATIONSEASONCOUNT = SEASONNUMBER;
    }

    @Override
    public void stateProcess() {
        SimulationContext simulationContextObject = contextAbstractFactory.createSimulationContextWithGameContext(ourGame);
        simulationContextObject.setInMemoryLeague(ourGame.getInMemoryLeague());
        simulationContextObject.setYear(ourGame.getYear());
        simulationContextObject.setGameConfig(ourGame.getInMemoryLeague().getGameConfig());
        simulationContextObject.setInMemoryLeague(ourGame.getInMemoryLeague());
        userInputOutput.printMessage("Total simulation season count: " + SIMULATIONSEASONCOUNT);

        GameSimulationAbstractFactory factory = GameSimulationAbstractFactory.instance();
        ISubject subject = factory.createSubject();
        IGameObserver goals = factory.createGoalObserver(subject);
        IGameObserver saves = factory.createSaveObserver(subject);
        IGameObserver penalities = factory.createPenaltyObserver(subject);
        IGameObserver shots = factory.createShotObserver(subject);

        for (int i = 1; i <= SIMULATIONSEASONCOUNT; i++) {
            simulationContextObject.setSubjectGameSimulation(subject);
            simulationContextObject.setSeasonInProgress(true);
            simulationContextObject.setCurrentSimulation(simulationContextObject.getInitializeSeason());

            logger.info("Season " + i + ": is in progress");
            while (simulationContextObject.isSeasonInProgress()) {
                simulationContextObject.seasonStateProcess();
                simulationContextObject.seasonStateExitProcess();
            }
            goals.print();
            saves.print();
            penalities.print();
            shots.print();
            ourGame.setYear(ourGame.getYear() + 1);
            simulationContextObject.setYear(ourGame.getYear());
            simulationContextObject.setNumberOfDays(0);
        }


    }

    @Override
    public void stateExitProcess() {
        ourGame.setGameInProgress(false);
    }
}
