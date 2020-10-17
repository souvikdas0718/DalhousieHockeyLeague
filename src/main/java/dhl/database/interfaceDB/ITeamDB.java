package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.ITeam;

public interface ITeamDB {
    public int insertTeam(ITeam team, int divisionId, int leagueId)  throws Exception;

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception;
}
