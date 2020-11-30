package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


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
import java.util.List;


public class SimulateGameState implements ISimulationSeasonState {
    private static final double RANDOMWINCHANCE = 0.9;
    private static final int DAY = 1;
    public static Logger logger = LogManager.getLogger(SimulateGameState.class);
    SimulationContext simulationContext;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    IStandingSystem standingSystem;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;

    public SimulateGameState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        injuryCheckTeams = new ArrayList<>();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
        userInputOutput = IUserInputOutput.getInstance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    private void winDecider(LocalDate currentDate, IScheduler scheduler) {
        logger.info("Into winDecider method, current Date: "+ currentDate);
        ITeam winningTeam;
        ITeam losingTeam;
        if (currentDate.isAfter(scheduler.getSeasonStartDate().minusDays(DAY)) && currentDate.isBefore(scheduler.getSeasonEndDate().plusDays(DAY))) {
            logger.debug("Checking for games lying in general playOffs ");
//            for (ISeasonSchedule schedule : scheduler.getFullSeasonSchedule()) {
//                if (schedule.getGameDate().equals(currentDate)) {
//                    logger.debug("Once schedule match date is same as current date, add teams for injury check and based on requirement deciding the winner");
//                    Double randomNumber = Math.random() * 100;
//                    injuryCheckTeams.add(schedule.getTeamOne());
//                    injuryCheckTeams.add(schedule.getTeamTwo());
//                    if (schedule.getTeamOne().calculateTeamStrength() > schedule.getTeamTwo().calculateTeamStrength()) {
//                        winningTeam = schedule.getTeamOne();
//                        losingTeam = schedule.getTeamTwo();
//                        if (randomNumber < RANDOMWINCHANCE) {
//                            winningTeam = schedule.getTeamTwo();
//                            losingTeam = schedule.getTeamOne();
//                        }
//                    } else {
//                        winningTeam = schedule.getTeamTwo();
//                        losingTeam = schedule.getTeamOne();
//                        if (randomNumber < RANDOMWINCHANCE) {
//                            winningTeam = schedule.getTeamOne();
//                            losingTeam = schedule.getTeamTwo();
//                        }
//                    }
//                    logger.debug("updating the winning and losing teams standings in Standing System class.");
//                    standingSystem.updateWinningStandings(winningTeam);
//                    standingSystem.updateLosingStandings(losingTeam);
//                }
//            }
            ISeasonSchedule match = simulationContext.getMatchToSimulate();
            Double randomNumber = Math.random() * 100;
            injuryCheckTeams.add(match.getTeamOne());
            injuryCheckTeams.add(match.getTeamTwo());
            if (match.getTeamOne().calculateTeamStrength() > match.getTeamTwo().calculateTeamStrength()) {
                winningTeam = match.getTeamOne();
                losingTeam = match.getTeamTwo();
                if (randomNumber < RANDOMWINCHANCE) {
                    winningTeam = match.getTeamTwo();
                    losingTeam = match.getTeamOne();
                }
            } else {
                winningTeam = match.getTeamTwo();
                losingTeam = match.getTeamOne();
                if (randomNumber < RANDOMWINCHANCE) {
                    winningTeam = match.getTeamOne();
                    losingTeam = match.getTeamTwo();
                }
            }
            logger.debug("updating the winning and losing teams standings in Standing System class.");
            standingSystem.updateWinningStandings(winningTeam);
            standingSystem.updateLosingStandings(losingTeam);

        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate().minusDays(DAY)) && currentDate.isBefore(scheduler.getFinalDay().plusDays(DAY))) {
            logger.debug("checking for playoffs games");
//            ArrayList<ISeasonSchedule> schedules = new ArrayList<>(scheduler.getPlayOffScheduleRound1());
//            for (ISeasonSchedule playOffSchedule : schedules) {
//                if (playOffSchedule.getGameDate().equals(currentDate)) {
//                    logger.debug("Once schedule match date is same as current date, add teams for injury check and based on requirement deciding the winner");
//                    Double randomNumber = Math.random() * 100;
//                    injuryCheckTeams.add(playOffSchedule.getTeamOne());
//                    injuryCheckTeams.add(playOffSchedule.getTeamTwo());
//                    if (playOffSchedule.getTeamOne().calculateTeamStrength() > playOffSchedule.getTeamTwo().calculateTeamStrength()) {
//                        winningTeam = playOffSchedule.getTeamOne();
//                        if (randomNumber < RANDOMWINCHANCE) {
//                            winningTeam = playOffSchedule.getTeamTwo();
//                        }
//                    } else {
//                        winningTeam = playOffSchedule.getTeamTwo();
//                        if (randomNumber < RANDOMWINCHANCE) {
//                            winningTeam = playOffSchedule.getTeamOne();
//                        }
//                    }
//                    logger.debug("calling the gameWim=nner method of scheduler class to set the next playOff lists");
//                    scheduler.gameWinner(winningTeam);
//                    logger.debug("setting the final schedule in the simulation context");
//                    simulationContext.setFinalSchedule(scheduler);
//                }
//            }
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
            logger.debug("calling the gameWim=nner method of scheduler class to set the next playOff lists");
            scheduler.gameWinner(winningTeam);
            logger.debug("setting the final schedule in the simulation context");
            simulationContext.setFinalSchedule(scheduler);
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
