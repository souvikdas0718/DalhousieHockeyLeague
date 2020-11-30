package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.traning.ITraining;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
    SimulationContext simulationContext;
    IScheduler scheduler;
    TrainingAbstractFactory trainingAbstractFactory;
    IUserInputOutput userInputOutput;

    public TrainingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        scheduler = simulationContext.getRegularScheduler();
        trainingAbstractFactory = TrainingAbstractFactory.instance();
        userInputOutput = IUserInputOutput.getInstance();
    }

    static void unPlayedGameAndTradingDeadline(LocalDate currentDate, IScheduler scheduler, SimulationContext simulationContext) {
        if (currentDate.isAfter(simulationContext.getSeasonStartDate().minusDays(DAY)) && currentDate.isBefore(simulationContext.getSeasonEndDate().plusDays(DAY))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else if (currentDate.isAfter(simulationContext.getPlayOffStartDate().minusDays(DAY)) && currentDate.isBefore(simulationContext.getFinalDay().plusDays(DAY))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else {
            LocalDate localDate = LocalDate.of(simulationContext.getYear() + YEAR, TRADEDEADLINEMONTH, TRADEDEADLINEDAY);
            LocalDate tradeDeadline = localDate.with(lastDayOfMonth())
                    .with(previousOrSame(DayOfWeek.MONDAY));
            if (currentDate.isBefore(tradeDeadline.plusDays(DAY)) && currentDate.isAfter(simulationContext.getStartOfSimulation())) {
                simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getAging());
            }
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

