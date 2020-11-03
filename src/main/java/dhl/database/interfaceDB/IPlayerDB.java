package dhl.database.interfaceDB;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;
import java.util.Map;

public interface IPlayerDB {

    int insertPlayer(IPlayer player, int teamId, int leagueId) throws Exception;

    void updatePlayer(IPlayer player, String teamName, String leagueName) throws Exception;

    void insertRetiredPlayers(ILeagueObjectModel leagueObjectModel,
                              Map<String, List<IPlayer>> playersToRetire,
                              List<IPlayer> freeAgentsToRetire) throws Exception;

    List<IPlayer> getPlayerList(int teamId, int leagueId) throws Exception;

}
