package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISchedule;
import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.Interface.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.SimulationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SimulateGameState implements ISimulationSeasonState {
    SimulationContext ourSeasonGame;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    IStandingSystem standingSystem;

    public SimulateGameState(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {
        scheduler = ourSeasonGame.getRegularScheduler();
        injuryCheckTeams = new ArrayList<>();

    }

    private void winDecider(LocalDate currentDate, IScheduler scheduler) {
        ITeam winningTeam;
        ITeam losingTeam;
        for (ISchedule schedule : scheduler.getFullSeasonSchedule()) {
            if (schedule.getGameDate().equals(currentDate)) {
                IGameConfig gameConfig = ourSeasonGame.getGameConfig();
                Double randomWinChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getGameResolver(), gameConfig.getRandomWinChance())) * 100;
                Double randomNumber = Math.random() * 100;
                injuryCheckTeams.add(schedule.getTeamOne());
                injuryCheckTeams.add(schedule.getTeamTwo());
                if (schedule.getTeamOne().calculateTeamStrength() > schedule.getTeamTwo().calculateTeamStrength()) {
                    winningTeam = schedule.getTeamOne();
                    losingTeam = schedule.getTeamTwo();
                    if (randomNumber < randomWinChance) {
                        winningTeam = schedule.getTeamTwo();
                        losingTeam = schedule.getTeamOne();
                    }
                } else {
                    winningTeam = schedule.getTeamTwo();
                    losingTeam = schedule.getTeamOne();
                    if (randomNumber < randomWinChance) {
                        winningTeam = schedule.getTeamOne();
                        losingTeam = schedule.getTeamTwo();
                    }
                }
                standingSystem = ourSeasonGame.getStandingSystem();
                if (currentDate.isAfter(scheduler.getSeasonStartDate()) && currentDate.isBefore(scheduler.getSeasonEndDate())) {
                    standingSystem.updateWinningStandings(winningTeam);
                    standingSystem.updateLosingStandings(losingTeam);
                } else if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())) {
                    scheduler.gameWinner(winningTeam);
                }
            }
        }
    }

    @Override
    public void seasonStateProcess() {
        LocalDate startOfSimulation = ourSeasonGame.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(ourSeasonGame.getNumberOfDays());
        winDecider(currentDate, scheduler);
    }

    @Override
    public void seasonStateExitProcess() {
        this.ourSeasonGame.setTeamsPlayingInGame(injuryCheckTeams);
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getInjuryCheck());

    }
}
