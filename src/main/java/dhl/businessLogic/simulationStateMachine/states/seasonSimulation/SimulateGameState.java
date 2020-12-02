package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.gameSimulation.GameSimulationAbstractFactory;
import dhl.businessLogic.gameSimulation.IGameSimulationAlgorithm;
import dhl.businessLogic.gameSimulation.ISubject;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SimulateGameState implements ISimulationSeasonState {
    private static final double RANDOMWINCHANCE = 0.9;
    private static final int DAY = 1;
    private static final int POINTS = 1;
    public static Logger logger = LogManager.getLogger(SimulateGameState.class);
    private static int finalWinnerDecider;
    SimulationContext simulationContext;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    IStandingSystem standingSystem;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;
    ISubject subject;

    public SimulateGameState(SimulationContext simulationContext) {
        logger.info("Into the Simulation game state constructor");
        this.simulationContext = simulationContext;
        injuryCheckTeams = new ArrayList<>();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
        userInputOutput = IUserInputOutput.getInstance();
        finalWinnerDecider = 0;
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    private void winDecider(LocalDate currentDate, IScheduler scheduler) {
        logger.info("Into winDecider method, current Date: " + currentDate);
        ITeam winningTeam;
        ITeam losingTeam;
        if (currentDate.isAfter(scheduler.getSeasonStartDate().minusDays(DAY)) && currentDate.isBefore(scheduler.getSeasonEndDate().plusDays(DAY))) {
            logger.debug("Checking for games lying in general playOffs ");
            ISeasonSchedule match = simulationContext.getMatchToSimulate();
            Double randomNumber = Math.random() * 100;
            injuryCheckTeams.add(match.getTeamOne());
            injuryCheckTeams.add(match.getTeamTwo());

            GameSimulationAbstractFactory factory = GameSimulationAbstractFactory.instance();
            IGameSimulationAlgorithm gameSimulationAlgorithm = factory.createGameSimulationAlgorithm();
            HashMap<String, Integer> mapResult = gameSimulationAlgorithm.getResultOfGame(match.getTeamOne(), match.getTeamTwo());
            subject.setState(mapResult);

            if (mapResult.get("Winner").toString().equals("1")) {
                winningTeam = match.getTeamOne();
                losingTeam = match.getTeamTwo();
            } else {
                winningTeam = match.getTeamTwo();
                losingTeam = match.getTeamOne();
            }

            userInputOutput.printMessage("regular season winning team: " + winningTeam.getTeamName());
            userInputOutput.printMessage("regular season losing team: " + losingTeam.getTeamName());
            logger.debug("updating the winning and losing teams standings in Standing System class.");
            standingSystem.updateWinningStandings(winningTeam);
            standingSystem.updateLosingStandings(losingTeam);
            losingTeam.setLossPoint(losingTeam.getLossPoint() + POINTS);

        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate().minusDays(DAY)) && currentDate.isBefore(scheduler.getFinalDay().plusDays(DAY))) {
            logger.debug("checking for playoffs games");
            ISeasonSchedule playOffMatch = simulationContext.getMatchToSimulate();
            Double randomNumber = Math.random() * 100;
            injuryCheckTeams.add(playOffMatch.getTeamOne());
            injuryCheckTeams.add(playOffMatch.getTeamTwo());
            if (playOffMatch.getTeamOne().calculateTeamStrength() > playOffMatch.getTeamTwo().calculateTeamStrength()) {
                winningTeam = playOffMatch.getTeamOne();
                if (randomNumber < RANDOMWINCHANCE) {
                    winningTeam = playOffMatch.getTeamTwo();
                }
            } else {
                winningTeam = playOffMatch.getTeamTwo();
                if (randomNumber < RANDOMWINCHANCE) {
                    winningTeam = playOffMatch.getTeamOne();
                }
            }

            userInputOutput.printMessage("Playoff winning team: " + winningTeam.getTeamName());
            logger.debug("calling the gameWinner method of scheduler class to set the next playOff lists");
            scheduler.gameWinner(winningTeam);
            logger.debug("setting the final schedule in the simulation context");
            simulationContext.setFinalSchedule(scheduler);

            finalWinnerDecider = finalWinnerDecider + 1;
            if (finalWinnerDecider == 15) {
                userInputOutput.printMessage("#################### Hurray Stanley Cup Winner Decided and the winning team is " + winningTeam.getTeamName() + " ###################");
            }

        }
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of simulate game season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        scheduler = simulationContext.getRegularScheduler();
        scheduler.setPlayOffStartDate(simulationContext.getPlayOffStartDate());
        scheduler.setFinalDay(simulationContext.getFinalDay());
        scheduler.setSeasonStartDate(simulationContext.getSeasonStartDate());
        scheduler.setSeasonEndDate(simulationContext.getSeasonEndDate());
        subject = simulationContext.getSubjectGameSimulation();
        logger.debug("Calling the winDecider method to simulate a game a decide the winner");
        winDecider(currentDate, scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the state process of simulate game season");
        this.simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());
    }
}