package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IDivisionDB;
import dhl.database.interfaceDB.ITeamDB;
import dhl.businessLogic.leagueModel.Division;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DivisionDB implements IDivisionDB {
    public int insertDivision(String divisionName, int conferenceId, int leagueId) throws Exception {
        int divisionId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertDivision(?,?,?)");
        callproc.setParameter(1, conferenceId);
        callproc.setParameter(2, divisionName);
        callproc.setParameter(3, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (results == null) {
            throw new Exception("Division not inserted properly");
        }
        while (results.next()) {
            divisionId = results.getInt("division_Id");
        }

        callproc.cleanup();

        return divisionId;
    }

    public List<IDivision> getDivisionList(int conferenceId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception {
        ITeamDB teamDB = databaseObjectCreationDB.getTeamDB();
        List<IDivision> divisionList = new ArrayList<>();
        CallStoredProcedure callDivisionProc = new CallStoredProcedure("loadDivisions(?,?)");
        callDivisionProc.setParameter(1, conferenceId);
        callDivisionProc.setParameter(2, leagueId);
        ResultSet divisionsResultSet = callDivisionProc.executeWithResults();

        if (divisionsResultSet == null) {
            throw new Exception("Error loading divisions");
        }

        while (divisionsResultSet.next()) {
            IDivision division = new Division(divisionsResultSet.getString("divisionName"),
                    teamDB.getTeamList(divisionsResultSet.getInt("divisionId"), leagueId, databaseObjectCreationDB));
            divisionList.add(division);
        }
        callDivisionProc.cleanup();
        return divisionList;
    }
}
