package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.StandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SimulateGameState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    IStandingSystem standingSystem;

    public SimulateGameState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        scheduler = this.simulationContext.getRegularScheduler();
        injuryCheckTeams = new ArrayList<>();
        standingSystem = new StandingSystem();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    private void winDecider(LocalDate currentDate, IScheduler scheduler) {

        double RANDOM_WIN_CHANCE = 0.9;
        ITeam winningTeam;
        ITeam losingTeam;
        for (ISeasonSchedule schedule : scheduler.getFullSeasonSchedule()) {
            if (schedule.getGameDate().equals(currentDate)) {
//                IGameConfig gameConfig = simulationContext.getGameConfig();
//                System.out.println(Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomWinChance())));
//                Double randomWinChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getGameResolver(), gameConfig.getRandomWinChance())) * 100;
                Double randomNumber = Math.random() * 100;
                injuryCheckTeams.add(schedule.getTeamOne());
                injuryCheckTeams.add(schedule.getTeamTwo());
                if (schedule.getTeamOne().calculateTeamStrength() > schedule.getTeamTwo().calculateTeamStrength()) {
                    winningTeam = schedule.getTeamOne();
                    losingTeam = schedule.getTeamTwo();
                    if (randomNumber < RANDOM_WIN_CHANCE) {
                        winningTeam = schedule.getTeamTwo();
                        losingTeam = schedule.getTeamOne();
                    }
                } else {
                    winningTeam = schedule.getTeamTwo();
                    losingTeam = schedule.getTeamOne();
                    if (randomNumber < RANDOM_WIN_CHANCE) {
                        winningTeam = schedule.getTeamOne();
                        losingTeam = schedule.getTeamTwo();
                    }
                }

                standingSystem.setStandingsList(scheduler.getGameStandings());

//                standingSystem= simulationContext.getStandingSystem();
//                System.out.println(standingSystem);
                if (currentDate.isAfter(scheduler.getSeasonStartDate()) && currentDate.isBefore(scheduler.getSeasonEndDate())) {
                    standingSystem.updateWinningStandings(winningTeam);
                    standingSystem.updateLosingStandings(losingTeam);
                }
//                } else if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())) {
//                    scheduler.gameWinner(winningTeam);
//                }
            }
        }

        //// new code
        for (ISeasonSchedule playOffSchedule : scheduler.getPlayOffScheduleRound1()) {
            if (playOffSchedule.getGameDate().equals(currentDate)) {
                if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())) {
                    Double randomNumber = Math.random() * 100;
                    injuryCheckTeams.add(playOffSchedule.getTeamOne());
                    injuryCheckTeams.add(playOffSchedule.getTeamTwo());
                    if (playOffSchedule.getTeamOne().calculateTeamStrength() > playOffSchedule.getTeamTwo().calculateTeamStrength()) {
                        winningTeam = playOffSchedule.getTeamOne();
                        if (randomNumber < RANDOM_WIN_CHANCE) {
                            winningTeam = playOffSchedule.getTeamTwo();
                        }
                    } else {
                        winningTeam = playOffSchedule.getTeamTwo();
                        if (randomNumber < RANDOM_WIN_CHANCE) {
                            winningTeam = playOffSchedule.getTeamOne();
                        }
                    }
                    scheduler.gameWinner(winningTeam);
                }
            }
        }

    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        winDecider(currentDate, scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        //Figure out how to set add teams for Playing in game.
        //Use getter and setter for injury check Teams
        this.simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());
    }
}
