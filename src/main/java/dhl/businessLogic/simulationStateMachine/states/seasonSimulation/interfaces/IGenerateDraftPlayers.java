package dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;

public interface IGenerateDraftPlayers {

    List<IPlayer> generateDraftPlayers();

    public List<IPlayer> getDraftPlayers();
}
