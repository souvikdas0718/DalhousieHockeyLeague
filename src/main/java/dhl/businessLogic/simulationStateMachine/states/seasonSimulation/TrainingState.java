package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.traning.ITraining;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
public class TrainingState implements ISimulationSeasonState {
    private static final double DAYSUNTILSTATINCREASECHECK = 193;
    SimulationContext simulationContext;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    TrainingAbstractFactory trainingAbstractFactory;
    IUserInputOutput userInputOutput;
    public TrainingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
//        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
//        scheduler = schedulerAbstractFactory.getScheduler();
        scheduler = simulationContext.getRegularScheduler();
        trainingAbstractFactory = TrainingAbstractFactory.instance();
        userInputOutput = IUserInputOutput.getInstance();
    }
    static void unPlayedGameAndTradingDeadline(LocalDate currentDate, IScheduler scheduler, SimulationContext simulationContext) {
        if (currentDate.isAfter(simulationContext.getSeasonStartDate().minusDays(1)) && currentDate.isBefore(simulationContext.getSeasonEndDate().plusDays(1))) {
//        if (currentDate.isAfter(scheduler.getSeasonStartDate().minusDays(1)) && currentDate.isBefore(scheduler.getSeasonEndDate().plusDays(1))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else if (currentDate.isAfter(simulationContext.getPlayOffStartDate().minusDays(1)) && currentDate.isBefore(simulationContext.getFinalDay().plusDays(1))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else {
            LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
            LocalDate tradeDeadline = localDate.with(lastDayOfMonth())
                    .with(previousOrSame(DayOfWeek.MONDAY));
            if (currentDate.isBefore(tradeDeadline.plusDays(1)) && currentDate.isAfter(simulationContext.getStartOfSimulation())) {
//            if (currentDate.isBefore(tradeDeadline) || currentDate.isEqual(tradeDeadline)) {
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
        userInputOutput.printMessage("Into the state process of Training State season");
        simulationContext.setDaysSinceLastTraining(simulationContext.getDaysSinceLastTraining() + 1);
        IGameConfig gameConfig = simulationContext.getGameConfig();
        //commenting as it is not available in m3
//        int daysUntilStatIncreaseCheck = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getDaysUntilStatIncreaseCheck()));
        try {
            if (DAYSUNTILSTATINCREASECHECK == simulationContext.getDaysSinceLastTraining()) {
                ITraining training = trainingAbstractFactory.createTraining(simulationContext.getInjurySystem(), simulationContext.getGameConfig());
//                ITraining training = new Training(simulationContext.getInjurySystem(), simulationContext.getGameConfig());
                training.updatePlayerStats(simulationContext.getInMemoryLeague());
                simulationContext.setDaysSinceLastTraining(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Training State season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
//        scheduler = simulationContext.getPlayOffScheduleRound1();
        //made this current change
        scheduler = simulationContext.getRegularScheduler();
        scheduler.setSeasonEndDate(simulationContext.getSeasonEndDate());
        scheduler.setSeasonStartDate(simulationContext.getSeasonStartDate());
        scheduler.setPlayOffStartDate(simulationContext.getPlayOffStartDate());
        scheduler.setFinalDay(simulationContext.getFinalDay());
        unPlayedGameAndTradingDeadline(currentDate, scheduler, simulationContext);
    }
}