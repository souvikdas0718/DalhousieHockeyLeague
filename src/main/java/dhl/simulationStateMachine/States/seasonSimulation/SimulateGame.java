package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.simulationStateMachine.States.seasonScheduler.Scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SimulateGame implements ISimulationSeasonState {
    public SimulateGame(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {
        // Dates must be persisted from initalised season
        LocalDate date = LocalDate.of(2020,11,01);
        // Any unPlayed game
        IScheduler scheduler = new Scheduler();
        if(date.isAfter(scheduler.getSeasonStartDate()) && date.isBefore(scheduler.getSeasonEndDate())){
            //generalSeason match
            for (ISchedule schedule: scheduler.getFullSeasonSchedule()) {
                if(schedule.getGameDate().equals(date)){
//                    schedule.getTeamOne().get
                    //simulate game and decide a winner
                    //send it to gameWinner method, either teamOne or teamTwo
                    scheduler.gameWinner(schedule.getTeamOne());
                }
            }
        }
        else if (date.isAfter(scheduler.getPlayOffStartDate()) && date.isBefore(scheduler.getFinalDay())){
            //playoffs match
            for (ISchedule schedule: scheduler.getFullSeasonSchedule()) {
                if(schedule.getGameDate().equals(date)){
//                    schedule.getTeamOne().get
                    //simulate game and decide a winner
                    //send it to gameWinner method, either teamOne or teamTwo
                    scheduler.gameWinner(schedule.getTeamOne());
                }
            }
        }
    }

    @Override
    public void seasonStateProcess() {
            // simulate one game win/loss
    }

    @Override
    public void seasonStateExitProcess() {
        // check injury
        //set an array teamInGame
//        move to injury check state
    }
}
