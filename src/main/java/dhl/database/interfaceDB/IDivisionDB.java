package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.IDivision;

import java.util.List;

public interface IDivisionDB {
    public int insertDivision(String divisionName, int conferenceId,int leagueId) throws Exception;

    public List<IDivision> getDivisionList(int conferenceId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;
}
