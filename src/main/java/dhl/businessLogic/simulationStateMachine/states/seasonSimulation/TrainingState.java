package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.traning.Training;
import dhl.businessLogic.traning.interfaces.ITraining;

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
        try {
            if (daysUntilStatIncreaseCheck == simulationContext.getDaysSinceLastTraining()) {
                ITraining training = new Training(simulationContext.getInjurySystem(), simulationContext.getGameConfig());
                training.updatePlayerStats(simulationContext.getInMemoryLeague());
                simulationContext.setDaysSinceLastTraining(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

