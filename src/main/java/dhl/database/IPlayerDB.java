package dhl.database;

public interface IPlayerDB {
    public int insertPlayer(String playerName, String playerPosition, boolean isCaptain, boolean isFreeAgent, int teamId, int leagueId )  throws Exception;
}
