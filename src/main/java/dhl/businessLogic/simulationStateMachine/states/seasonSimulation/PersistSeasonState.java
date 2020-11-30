package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

public class PersistSeasonState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;
    private static final Logger logger = LogManager.getLogger(PersistSeasonState.class);


    public PersistSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
        userInputOutput = IUserInputOutput.getInstance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        userInputOutput.printMessage("Into the state process of Persist season");
        SerializeDeserializeAbstractFactory factorySerialize = SerializeDeserializeAbstractFactory.instance();
        ISerializeLeagueObjectModel serializeLeagueObjectModel = factorySerialize.createSerializeLeagueObjectModel("src/SerializedJsonFiles/");
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        try {
            leagueObjectModel.updateLeagueObjectModel(serializeLeagueObjectModel);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error occurred during Persist season state: " + e.getMessage());
            simulationContext.seasonStateExitProcess();
        }
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Persist same season");
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.isBefore(LocalDate.of(simulationContext.getYear() + 1, 9, 29))) {
            simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        } else {
            simulationContext.setSeasonInProgress(false);
        }
    }
}

