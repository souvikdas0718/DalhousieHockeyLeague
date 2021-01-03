package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;

public class AdvanceToNextSeasonState implements ISimulationSeasonState {
    private static final int ENDOFSEASONMONTH = 9;
    private static final int ENDOFSEASONDAY = 29;
    private static final int TOTALDAYSINAYEAR = 365;
    private static final int YEAR = 1;
    private static final Logger logger = LogManager.getLogger(AdvanceToNextSeasonState.class);
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;
    static AgingAbstractFactory agingFactory;


    public AdvanceToNextSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
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
        logger.info("Into the state process of Advance to next season");
        agingCalculation(simulationContext);
        simulationContext.setYear(simulationContext.getYear() + YEAR);
        LocalDate endOfSeason = LocalDate.of(simulationContext.getYear(), ENDOFSEASONMONTH, ENDOFSEASONDAY);
        simulationContext.setEndOfSimulation(endOfSeason);
        simulationContext.setNumberOfDays(TOTALDAYSINAYEAR);

    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Adv to next season");
        simulationContext.setCurrentSimulation(simulationContext.getPersistsSeason());
    }

    static void agingCalculation(SimulationContext simulationContext) {
        ILeagueSchedule leagueSchedule = agingFactory.createLeagueSchedule(simulationContext.getInMemoryLeague());
        try {
            LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
            LocalDate currentDate = startOfSimulation.plusDays(TOTALDAYSINAYEAR);
            leagueSchedule.initiateAging(TOTALDAYSINAYEAR, currentDate);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }
}
