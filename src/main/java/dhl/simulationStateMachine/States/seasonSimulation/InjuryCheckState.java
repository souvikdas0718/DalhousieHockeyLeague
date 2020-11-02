package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.IScheduler;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class InjuryCheckState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    IScheduler scheduler;
    public InjuryCheckState(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess()  {
        // check if players are injured
        //each team we have to check check injury method
        for(ITeam team : simulationContext.getTeamsPlayingInGame()){
            IInjurySystem injurySystem=simulationContext.getInjurySystem();
            injurySystem.checkTeamInjury(simulationContext.getGameConfig(),team);
            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
        }
    }

    @Override
    public void seasonStateExitProcess() {
        scheduler = simulationContext.getRegularScheduler();
        LocalDate startOfSimulation=simulationContext.getStartOfSimulation();
        LocalDate currentDate= startOfSimulation.plusDays(simulationContext.getNumberOfDays());


        if(currentDate.isAfter(scheduler.getSeasonStartDate()) && currentDate.isBefore(scheduler.getSeasonEndDate())){
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());

        }
        else if (currentDate.isAfter(scheduler.getPlayOffStartDate()) && currentDate.isBefore(scheduler.getFinalDay())){
            simulationContext.setCurrentSimulation(simulationContext.getSimulateGame());
        }
        else {
            LocalDate localDate = LocalDate.of(simulationContext.getYear() + 1,02,01);
            LocalDate regularSeasonEndDate = localDate.with(lastDayOfMonth())
                    .with(previousOrSame(DayOfWeek.MONDAY));
            if(currentDate.isBefore(regularSeasonEndDate) || currentDate.isEqual(regularSeasonEndDate)){
                simulationContext.setCurrentSimulation(simulationContext.getExecuteTrades());
            }
            else {
                simulationContext.setCurrentSimulation(simulationContext.getAging());
            }
        }
    }
}
