package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IAging {
    void agePlayers(List<IPlayer> players, LocalDate currentDate);

    Map<String, List<IPlayer>> selectPlayersToRetire(ITeam team, Map<String, List<IPlayer>> retiringPlayers);

    List<IPlayer> selectFreeAgentsToRetire(List<IPlayer> freeAgents);

}
