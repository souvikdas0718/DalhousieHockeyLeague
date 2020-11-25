package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

import java.time.LocalDate;

public class PersistSeasonState implements ISimulationSeasonState {
    SimulationContext simulationContext;

    public PersistSeasonState(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        // save the data in the db
//        SerializeDeserializeAbstractFactory factorySerialize = SerializeDeserializeAbstractFactory.instance();
//        ISerializeLeagueObjectModel serializeLeagueObjectModel = factorySerialize.createSerializeLeagueObjectModel("src/SerializedJsonFiles/");
//        LeagueObjectModel leagueObjectModel = new LeagueObjectModel();
//        leagueObjectModel.updateLeagueObjectModel(serializeLeagueObjectModel, simulationContext.getInMemoryLeague());
//                call this method
//        inMemoryLeague.updateLeagueObjectModel(serializeLeagueObjectModel, inMemoryLeague)
    }

    @Override
    public void seasonStateExitProcess() {
        LocalDate startOfSimulation = simulationContext.getStartOfSimulation();
        LocalDate currentDate = startOfSimulation.plusDays(simulationContext.getNumberOfDays());
        if (currentDate.isBefore(LocalDate.of(simulationContext.getYear() + 1, 9, 29))) {
            simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
        } else {
            simulationContext.setSeasonInProgress(false);
        }
    }
}

