package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.simulationStateMachine.States.seasonScheduler.Scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SimulateGameState implements ISimulationSeasonState {
    SimulationContext ourSeasonGame;
    List<ITeam> injuryCheckTeams;
    IScheduler scheduler;
    public SimulateGameState(SimulationContext simulationContext) {
        ourSeasonGame = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {
        // Dates must be persisted from initalised season
////        LocalDate date = LocalDate.of(2020,11,01);
        scheduler = ourSeasonGame.getRegularScheduler();
        injuryCheckTeams = new ArrayList<>();

    }

    private void winDecider(LocalDate currentDate, IScheduler scheduler) {
        ITeam winningTeam;
        for (ISchedule schedule: scheduler.getFullSeasonSchedule()) {
            if(schedule.getGameDate().equals(currentDate)){
                IGameConfig gameConfig = ourSeasonGame.getGameConfig();
                Double randomWinChance = Double.parseDouble( gameConfig.getValueFromOurObject(gameConfig.getGameResolver(), gameConfig.getRandomWinChance()))* 100;
                Double randomNumber = Math.random()*100;
                injuryCheckTeams.add(schedule.getTeamOne());
                injuryCheckTeams.add(schedule.getTeamTwo());
                if(schedule.getTeamOne().calculateTeamStrength() > schedule.getTeamTwo().calculateTeamStrength()){
                    winningTeam=schedule.getTeamOne();
                    if(randomNumber<randomWinChance){
                        winningTeam = schedule.getTeamTwo();
                    }
                }
                else{
                    winningTeam = schedule.getTeamTwo();
                    if(randomNumber<randomWinChance){
                        winningTeam = schedule.getTeamOne();
                    }
                }
                scheduler.gameWinner(winningTeam);
            }
        }
    }

    @Override
    public void seasonStateProcess() {
        LocalDate startOfSimulation=ourSeasonGame.getStartOfSimulation();
        LocalDate currentDate= startOfSimulation.plusDays(ourSeasonGame.getNumberOfDays());
        winDecider(currentDate, scheduler);

    }

    @Override
    public void seasonStateExitProcess() {
        // check injury
        //set an array teamInGame
//        move to injury check state
        this.ourSeasonGame.setTeamsPlayingInGame(injuryCheckTeams);
        ourSeasonGame.setCurrentSimulation(ourSeasonGame.getInjuryCheck());

    }
}
