package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;

public interface ILoadTeamStateLogic {
    Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague, GameContext ourGame, IDeserializeLeagueObjectModel deserializeLeagueObjectModel) throws Exception;
}
