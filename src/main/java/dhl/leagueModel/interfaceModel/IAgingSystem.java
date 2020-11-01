package dhl.leagueModel.interfaceModel;

import java.util.List;
import java.util.Map;

public interface IAgingSystem {
    void ageAllPlayers(List<IPlayer> players);

    Map<String,List<IPlayer>> selectPlayersToRetire(ITeam team);

    List<IPlayer> selectFreeAgentsToRetire(List<IPlayer> freeAgents);

}
