package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;

public class InjuryCheckState implements ISimulationSeasonState {
    public static Logger logger = LogManager.getLogger(InjuryCheckState.class);
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
        logger.info("Into the state process of Injury check season");
        for (ITeam team : simulationContext.getTeamsPlayingInGame()) {
            IInjury injury = agingFactory.createInjury();
            logger.debug("Checking the injury of each team");
            injury.checkTeamInjury(simulationContext.getGameConfig(), team);
            logger.debug("Resetting the Teams Playing in game for each entry");
            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
        }
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Injury check season");
        scheduler = simulationContext.getPlayOffScheduleRound1();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        simulationContext.setNumberOfDays(simulationContext.getNumberOfDays() + 1);
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        logger.debug("Calling the training state method to check for the unplayed games schedules");
        TrainingState.unPlayedGameAndTradingDeadline(currentDate, scheduler, simulationContext);
    }
}

