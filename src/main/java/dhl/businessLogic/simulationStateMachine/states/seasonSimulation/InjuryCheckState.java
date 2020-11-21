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

        // Check for unPlayed games by checking the schedules on that particular day and then either proceed for trading or aging or continue to simulate game
        TrainingState.unPlayedGameAndTradingDeadline(currentDate, scheduler, simulationContext);
    }
}

