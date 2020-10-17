package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.IFreeAgent;

public interface IFreeAgentDB {
    public int insertFreeAgent(IFreeAgent freeAgent, int leagueId )  throws Exception;
}
