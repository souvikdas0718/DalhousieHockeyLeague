package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.List;

public interface IFreeAgentDB {

    public int insertFreeAgent(IPlayer freeAgent, int leagueId )  throws Exception;

    public List<IPlayer> getFreeAgentList(int leagueId) throws Exception;

}
