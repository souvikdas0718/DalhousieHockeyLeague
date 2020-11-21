package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class InjuryCheckState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    IScheduler scheduler;

    public InjuryCheckState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        scheduler = new Scheduler();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        for (ITeam team : simulationContext.getTeamsPlayingInGame()) {
            IInjury injury = simulationContext.getInjurySystem();
            injury.checkTeamInjury(simulationContext.getGameConfig(), team);
            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
        }
    }

    @Override
    public void seasonStateExitProcess() {
//        scheduler = simulationContext.getRegularScheduler();
        scheduler = simulationContext.getPlayOffScheduleRound1();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());

        // Check for unplayed games by checking the schedules on that particular day and then either proceed for trading or aging or continue to simulate game
        if (currentDate.isAfter(scheduler.getSeasonStartDate().minusDays(1)) && currentDate.isBefore(scheduler.getSeasonEndDate().plusDays(1))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate().minusDays(1)) && currentDate.isBefore(scheduler.getFinalDay().plusDays(1))) {
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        } else {
            LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1, 02, 01);
            LocalDate tradeDeadline = localDate.with(lastDayOfMonth())
                    .with(previousOrSame(DayOfWeek.MONDAY));
            if (currentDate.isBefore(tradeDeadline) || currentDate.isEqual(tradeDeadline)) {
                simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getAging());
            }
        }
    }
}

