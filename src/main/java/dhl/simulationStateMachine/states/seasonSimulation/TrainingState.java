package dhl.simulationStateMachine.states.seasonSimulation;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.Training;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class TrainingState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IScheduler scheduler;

    public TrainingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        simulationContext.setDaysSinceLastTraining(simulationContext.getDaysSinceLastTraining() + 1);
        IGameConfig gameConfig = simulationContext.getGameConfig();
        int daysUntilStatIncreaseCheck = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getDaysUntilStatIncreaseCheck()));
        if (daysUntilStatIncreaseCheck == simulationContext.getDaysSinceLastTraining()) {
            ITraining training = new Training(simulationContext.getInjurySystem());
            training.updatePlayerStats(simulationContext.getInMemoryLeague());
            simulationContext.setDaysSinceLastTraining(0);
        }
    }

    @Override
    public void seasonStateExitProcess() {
        scheduler = simulationContext.getRegularScheduler();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());

        if (currentDate.isAfter(scheduler.getSeasonStartDate()) && currentDate.isBefore(scheduler.getSeasonEndDate())) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else {
            LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
            LocalDate regularSeasonEndDate = localDate.with(lastDayOfMonth())
                    .with(previousOrSame(DayOfWeek.MONDAY));
            if (currentDate.isBefore(regularSeasonEndDate) || currentDate.isEqual(regularSeasonEndDate)) {
                simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getAging());
            }
        }
    }
}
