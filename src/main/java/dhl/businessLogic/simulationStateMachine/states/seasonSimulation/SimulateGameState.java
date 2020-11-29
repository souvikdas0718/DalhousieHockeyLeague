package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SimulateGameState implements ISimulationSeasonState {
    private static final double RANDOMWINCHANCE = 0.9;
    SimulationContext simulationContext;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    IStandingSystem standingSystem;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;

    public SimulateGameState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
//        scheduler = this.simulationContext.getRegularScheduler();
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
        ITeam winningTeam;
        ITeam losingTeam;
        if (currentDate.isAfter(scheduler.getSeasonStartDate().minusDays(1)) && currentDate.isBefore(scheduler.getSeasonEndDate().plusDays(1))) {
            for (ISeasonSchedule schedule : scheduler.getFullSeasonSchedule()) {
                if (schedule.getGameDate().equals(currentDate)) {
                    Double randomNumber = Math.random() * 100;
                    injuryCheckTeams.add(schedule.getTeamOne());
                    injuryCheckTeams.add(schedule.getTeamTwo());
                    if (schedule.getTeamOne().calculateTeamStrength() > schedule.getTeamTwo().calculateTeamStrength()) {
                        winningTeam = schedule.getTeamOne();
                        losingTeam = schedule.getTeamTwo();
                        if (randomNumber < RANDOMWINCHANCE) {
                            winningTeam = schedule.getTeamTwo();
                            losingTeam = schedule.getTeamOne();
                        }
                    } else {
                        winningTeam = schedule.getTeamTwo();
                        losingTeam = schedule.getTeamOne();
                        if (randomNumber < RANDOMWINCHANCE) {
                            winningTeam = schedule.getTeamOne();
                            losingTeam = schedule.getTeamTwo();
                        }
                    }


                    standingSystem.updateWinningStandings(winningTeam);
                    standingSystem.updateLosingStandings(losingTeam);
//                    standingSystem.setStandingsList(scheduler.getGameStandings());
//                    simulationContext.setStandings(standingSystem.getStandingsList());
                }
            }
        } else if (currentDate.isAfter(scheduler.getPlayOffStartDate().minusDays(1)) && currentDate.isBefore(scheduler.getFinalDay().plusDays(1))) {
            ArrayList<ISeasonSchedule> schedules = new ArrayList<>(scheduler.getPlayOffScheduleRound1());
            for (ISeasonSchedule playOffSchedule : schedules) {
                if (playOffSchedule.getGameDate().equals(currentDate)) {

                    Double randomNumber = Math.random() * 100;
                    injuryCheckTeams.add(playOffSchedule.getTeamOne());
                    injuryCheckTeams.add(playOffSchedule.getTeamTwo());
                    if (playOffSchedule.getTeamOne().calculateTeamStrength() > playOffSchedule.getTeamTwo().calculateTeamStrength()) {
                        winningTeam = playOffSchedule.getTeamOne();
                        if (randomNumber < RANDOMWINCHANCE) {
                            winningTeam = playOffSchedule.getTeamTwo();
                        }
                    } else {
                        winningTeam = playOffSchedule.getTeamTwo();
                        if (randomNumber < RANDOMWINCHANCE) {
                            winningTeam = playOffSchedule.getTeamOne();
                        }
                    }
                    scheduler.gameWinner(winningTeam);
                    simulationContext.setFinalSchedule(scheduler);
                }
            }
        }

    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of simulate game season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        scheduler = simulationContext.getRegularScheduler();
        scheduler.setPlayOffStartDate(simulationContext.getPlayOffStartDate());
        scheduler.setFinalDay(simulationContext.getFinalDay());
        scheduler.setSeasonStartDate(simulationContext.getSeasonStartDate());
        scheduler.setSeasonEndDate(simulationContext.getSeasonEndDate());
        winDecider(currentDate, scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the state process of simulate game season");
        //Figure out how to set add teams for Playing in game.
        //Use getter and setter for injury check Teams
        this.simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());
    }
}
