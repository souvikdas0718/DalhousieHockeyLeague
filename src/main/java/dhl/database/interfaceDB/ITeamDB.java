package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.List;

public interface ITeamDB {
    int insertTeam(ITeam team, int divisionId, int leagueId) throws Exception;

    void updateTeam(ITeam team, String divisionName, String leagueName) throws Exception;

    boolean checkIfTeamAlreadyExists(String teamName, String divisionName) throws Exception;

    List<ITeam> getTeamList(int divisionId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;
}
