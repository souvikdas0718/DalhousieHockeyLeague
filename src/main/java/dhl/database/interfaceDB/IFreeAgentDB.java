package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.List;

public interface IFreeAgentDB {

    int insertFreeAgent(IPlayer freeAgent, int leagueId) throws Exception;

    List<IPlayer> getFreeAgentList(int leagueId) throws Exception;

}
