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

public class PersistSameSeasonState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    IUserInputOutput userInputOutput;
    private static final Logger logger = LogManager.getLogger(PersistSameSeasonState.class);


    public PersistSameSeasonState(SimulationContext simulationContext) {
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
        userInputOutput.printMessage("Into the state process of Persist same season");
        try {
        SerializeDeserializeAbstractFactory factorySerialize = SerializeDeserializeAbstractFactory.instance();
        ISerializeLeagueObjectModel serializeLeagueObjectModel = factorySerialize.createSerializeLeagueObjectModel("src/SerializedJsonFiles/");
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();

            leagueObjectModel.updateLeagueObjectModel(serializeLeagueObjectModel);
        } catch (IOException e) {
            logger.error("Error occurred during persist same season state: " + e.getMessage());
            simulationContext.seasonStateExitProcess();
        }
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Persist same season");
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
    }
}
