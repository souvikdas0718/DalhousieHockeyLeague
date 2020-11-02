package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.List;

public interface ITeamDB {
    public int insertTeam(ITeam team, int divisionId, int leagueId)  throws Exception;

    public void updateTeam(ITeam team, String divisionName, String leagueName)  throws Exception;

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception;

    public List<ITeam> getTeamList(int divisionId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB ) throws Exception;
}
