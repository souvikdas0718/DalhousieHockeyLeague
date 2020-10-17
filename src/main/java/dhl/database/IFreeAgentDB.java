package dhl.database;

public interface IFreeAgentDB {
    public int insertFreeAgent(String playerName, String playerPosition, int leagueId )  throws Exception;
}
