package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.IPlayer;

public interface IFreeAgentDB {
    public int insertFreeAgent(IPlayer freeAgent, int leagueId )  throws Exception;
}
