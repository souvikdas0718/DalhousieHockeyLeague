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

public class PersistSeasonState implements ISimulationSeasonState {
    public static Logger logger = LogManager.getLogger(PersistSeasonState.class);
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;

    public PersistSeasonState(SimulationContext simulationContext) {
        logger.info("Into Persist same season state constructor");
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
        logger.info("Into the state process of Persist season");
        SerializeDeserializeAbstractFactory factorySerialize = SerializeDeserializeAbstractFactory.instance();
        ISerializeLeagueObjectModel serializeLeagueObjectModel = factorySerialize.createSerializeLeagueObjectModel("src/SerializedJsonFiles/");
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        logger.debug("Calling the update League Model method to store the current game state in JSON file");
        try {
            leagueObjectModel.updateLeagueObjectModel(serializeLeagueObjectModel);
        } catch (IOException e) {
            logger.error(e.getMessage());
            simulationContext.seasonStateExitProcess();
        }
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Persist same season");
        logger.debug("End of a season");
        simulationContext.setSeasonInProgress(false);
    }
}

