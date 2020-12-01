package dhl.businessLogic.leagueModel.interfaceModel;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;

public interface IGenerateDraftPlayers {

    List<IPlayer> generateDraftPlayers();

    List<IPlayer> getDraftPlayers();
}
