package dhl.leagueModelTests;

import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.interfaceModel.IFreeAgent;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.List;
import java.util.Map;

public class IPlayerDBMock implements IPlayerDB {

    public int insertPlayer(IPlayer player, int teamId, int leagueId) throws Exception {
        return 0;
    }

    public void insertInjuredPlayer(IPlayer player, int teamId, int leagueId) throws Exception {

    }


    public void insertRetiredPlayers(ILeagueObjectModel leagueObjectModel, Map<String, List<IPlayer>> playersToRetire, Map<String, List<IFreeAgent>> freeAgentsToRetire) throws Exception {

    }
}
