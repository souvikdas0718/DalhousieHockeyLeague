package dhl.database;

public interface ITeamDB {
    public int insertTeam(String teamName, String generalManager, String headCoach, int divisionId,int leagueId)  throws Exception;

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception;
}
