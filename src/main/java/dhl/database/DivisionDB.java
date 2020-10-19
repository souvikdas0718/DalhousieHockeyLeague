package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IDivisionDB;

import java.sql.ResultSet;

public class DivisionDB implements IDivisionDB {
    public int insertDivision(String divisionName, int conferenceId, int leagueId) throws Exception {
        int divisionId=0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertDivision(?,?,?)");
        callproc.setParameter(1, conferenceId);
        callproc.setParameter(2, divisionName);
        callproc.setParameter(3, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                divisionId = results.getInt("division_Id");
            }
        }
        else {
            throw new Exception("Division not inserted properly");
        }

        callproc.cleanup();

        return divisionId;
    }

}
