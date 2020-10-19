package dhl.database.interfaceDB;

public interface IDivisionDB {
    public int insertDivision(String divisionName, int conferenceId,int leagueId) throws Exception;
}
