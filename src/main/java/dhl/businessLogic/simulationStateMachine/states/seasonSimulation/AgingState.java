package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.SeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class AgingState implements ISimulationSeasonState {
    public static Logger log = LogManager.getLogger(AgingState.class);
    static AgingAbstractFactory agingFactory;
    static SerializeDeserializeAbstractFactory serializeDeserializeAbstractFactory;
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;

    public AgingState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        userInputOutput = IUserInputOutput.getInstance();
        agingFactory = AgingAbstractFactory.instance();
    }

    static void agingCalculation(SimulationContext simulationContext) {
        serializeDeserializeAbstractFactory = SerializeDeserializeAbstractFactory.instance();
//        IAging aging = agingFactory.createAging(simulationContext.getGameConfig());
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        ISerializeLeagueObjectModel serializeModel = serializeDeserializeAbstractFactory.createSerializeLeagueObjectModel(leagueObjectModel.getLeagueName());
        ILeagueSchedule leagueSchedule = (LeagueSchedule) agingFactory.createLeagueSchedule(simulationContext.getInMemoryLeague());

        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        System.out.println("Current Date "+currentDate);
        System.out.println("Get no of days "+simulationContext.getNumberOfDays());
        leagueSchedule.initiateAging(simulationContext.getNumberOfDays(), currentDate);

    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of Aging State season");
        agingCalculation(simulationContext);
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Aging State season");
//        changed recently
//        IScheduler scheduler = simulationContext.getPlayOffScheduleRound1();
        IScheduler scheduler = simulationContext.getRegularScheduler();
//        ISeasonSchedule playoffSchedule = (SeasonSchedule) simulationContext.getRegularScheduler().getPlayOffScheduleRound1();
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if(null == scheduler) {
            userInputOutput.printMessage("No current Schedule");
            simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
        }
        else {
            if (scheduler.stanleyCupWinner(currentDate)) {
                simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
            } else {
                simulationContext.setCurrentSimulation(simulationContext.getPersistsSameSeason());
            }
        }
    }
}
