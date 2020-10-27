package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.IPlayer;

public interface IPlayerDB {
    public int insertPlayer(IPlayer player, int teamId, int leagueId )  throws Exception;
    public void insertInjuredPlayer(IPlayer player, int teamId, int leagueId )  throws Exception;
}
