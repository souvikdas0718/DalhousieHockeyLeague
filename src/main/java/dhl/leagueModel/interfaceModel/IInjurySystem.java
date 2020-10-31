package dhl.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;

public interface IInjurySystem {
    void checkTeamInjury(IGameConfig  gameConfig, ITeam team);

    boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player);

    void healInjuredPlayers(IPlayer player);
}
