package dhl.businessLogic.aging.Interface;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IInjurySystem {
    void checkTeamInjury(IGameConfig gameConfig, ITeam team);

    boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player);

    void healInjuredPlayers(IPlayer player);
}
