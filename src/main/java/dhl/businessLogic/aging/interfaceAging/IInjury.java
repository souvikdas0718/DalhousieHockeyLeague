package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IInjury {
    void checkTeamInjury(IGameConfig gameConfig, ITeam team);

    boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player, ITeam team);

    void healInjuredPlayers(IPlayer player);

    void healInjuredPlayersInTeam(IPlayer player, ITeam team);

}
