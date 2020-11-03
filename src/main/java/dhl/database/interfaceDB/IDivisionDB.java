package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;

import java.util.List;

public interface IDivisionDB {
    int insertDivision(String divisionName, int conferenceId, int leagueId) throws Exception;

    List<IDivision> getDivisionList(int conferenceId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;
}
