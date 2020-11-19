package dhl.businessLogic.simulationStateMachine.states.interfaces;

import dhl.inputOutput.importJson.interfaces.IDeserializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;

public interface ILoadTeamStateLogic {
    Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague, GameContext ourGame, IDeserializeLeagueObjectModel deserializeLeagueObjectModel) throws Exception;

}
