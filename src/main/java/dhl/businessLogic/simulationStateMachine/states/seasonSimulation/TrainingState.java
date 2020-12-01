package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.traning.ITraining;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class TrainingState implements ISimulationSeasonState {
    private static final double DAYSUNTILSTATINCREASECHECK = 193;
    private static final int YEAR = 1;
    private static final int DAY = 1;
    private static final int RESETTRAININGDATE = 0;
    private static final int TRADEDEADLINEMONTH = 2;
    private static final int TRADEDEADLINEDAY = 1;
    public static Logger logger = LogManager.getLogger(TrainingState.class);
    private static List<ISeasonSchedule> matchList;
    SimulationContext simulationContext;
    IScheduler scheduler;
    TrainingAbstractFactory trainingAbstractFactory;
    IUserInputOutput userInputOutput;

    public TrainingState(SimulationContext simulationContext) {
        logger.info("Into the training state constructor");
        this.simulationContext = simulationContext;
        scheduler = simulationContext.getRegularScheduler();
        trainingAbstractFactory = TrainingAbstractFactory.instance();
        userInputOutput = IUserInputOutput.getInstance();
        matchList = new ArrayList<>();
    }

    static void unPlayedGameAndTradingDeadline(LocalDate currentDate, IScheduler scheduler, SimulationContext simulationContext) {
        logger.info("conducting the unplayed games as per the current date present in game schedule.");
        if (currentDate.isAfter(simulationContext.getSeasonStartDate().minusDays(1)) && currentDate.isBefore(simulationContext.getSeasonEndDate().plusDays(1))) {
            logger.debug("creating a matchList for a single day and then storing it for simulating games for general season games");
            for (ISeasonSchedule schedule : scheduler.getFullSeasonSchedule()) {
                if (currentDate.equals(schedule.getGameDate()) && !schedule.isMatchToBePlayed()) {
                    schedule.setMatchToBePlayed(true);
                    matchList.add(schedule);
                } else if (schedule.getGameDate().isAfter(currentDate)) {
                    break;
                }
            }

        } else if (currentDate.isAfter(simulationContext.getPlayOffStartDate().minusDays(1)) && currentDate.isBefore(simulationContext.getFinalDay().plusDays(1))) {
            for (ISeasonSchedule schedule : scheduler.getPlayOffScheduleRound1()) {
                logger.debug("creating a matchList for a single day and then storing it for simulating games for playoffs season games");
                if (currentDate.equals(schedule.getGameDate()) && !schedule.isMatchToBePlayed()) {
                    schedule.setMatchToBePlayed(true);
                    matchList.add(schedule);
                } else if (schedule.getGameDate().isAfter(currentDate)) {
                    break;
                }
            }
        }

        for (ISeasonSchedule match : matchList) {
            logger.debug("Based on the match list simulating the game an calling Simulate game state");
            if (!match.isMatchPlayed()) {
                match.setMatchPlayed(true);
                simulationContext.setMatchToSimulate(match);
                simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
                simulationContext.seasonStateProcess();
                simulationContext.seasonStateExitProcess();
            }
        }

        LocalDate localDate = LocalDate.of(simulationContext.getYear() + YEAR, TRADEDEADLINEMONTH, TRADEDEADLINEDAY);
        LocalDate tradeDeadline = localDate.with(lastDayOfMonth())
                .with(previousOrSame(DayOfWeek.MONDAY));
        if (currentDate.isBefore(tradeDeadline.plusDays(DAY)) && currentDate.isAfter(simulationContext.getStartOfSimulation())) {
            logger.debug("trade deadline not passed so moving to execute trades state");
            simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
        } else {
            logger.debug("trade deadline has passed so moving to aging state");
            simulationContext.setCurrentSimulation(simulationContext.getAging());
        }
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of Training State season");
        simulationContext.setDaysSinceLastTraining(simulationContext.getDaysSinceLastTraining() + DAY);
        try {
            if (DAYSUNTILSTATINCREASECHECK == simulationContext.getDaysSinceLastTraining()) {
                ITraining training = trainingAbstractFactory.createTraining(simulationContext.getInjurySystem(), simulationContext.getGameConfig());
                training.updatePlayerStats(simulationContext.getInMemoryLeague());
                simulationContext.setDaysSinceLastTraining(RESETTRAININGDATE);
            }
        } catch (Exception e) {
            userInputOutput.printMessage(e.getMessage());
        }
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Training State season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        scheduler = simulationContext.getRegularScheduler();
        scheduler.setSeasonEndDate(simulationContext.getSeasonEndDate());
        scheduler.setSeasonStartDate(simulationContext.getSeasonStartDate());
        scheduler.setPlayOffStartDate(simulationContext.getPlayOffStartDate());
        scheduler.setFinalDay(simulationContext.getFinalDay());
        unPlayedGameAndTradingDeadline(currentDate, scheduler, simulationContext);
    }
}

