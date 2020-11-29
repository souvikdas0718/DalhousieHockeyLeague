package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.time.LocalDate;
import java.util.ArrayList;

public class InjuryCheckState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    IUserInputOutput userInputOutput;
    AgingAbstractFactory agingFactory;

    public InjuryCheckState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        scheduler = schedulerAbstractFactory.getScheduler();
        userInputOutput = IUserInputOutput.getInstance();
        agingFactory = AgingAbstractFactory.instance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of Injury check season");
        for (ITeam team : simulationContext.getTeamsPlayingInGame()) {
//            IInjury injury = simulationContext.getInjurySystem();
            IInjury injury = agingFactory.createInjury();
            injury.checkTeamInjury(simulationContext.getGameConfig(), team);
            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
        }
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Injury check season");
//        scheduler = simulationContext.getRegularScheduler();
        scheduler = simulationContext.getPlayOffScheduleRound1();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays()+1);
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());

        // Check for unPlayed games by checking the schedules on that particular day and then either proceed for trading or aging or continue to simulate game
        TrainingState.unPlayedGameAndTradingDeadline(currentDate, scheduler, simulationContext);
    }
}

