package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.List;
import java.util.Map;

public interface IPlayerDB {

    public int insertPlayer(IPlayer player, int teamId, int leagueId )  throws Exception;

    public void updatePlayer(IPlayer player, String teamName, String leagueName )  throws Exception;

    public void insertRetiredPlayers(ILeagueObjectModel leagueObjectModel,
                                     Map<String, List<IPlayer>> playersToRetire,
                                     List<IPlayer> freeAgentsToRetire)  throws Exception;

    public List<IPlayer> getPlayerList(int teamId,int leagueId) throws Exception;

}
