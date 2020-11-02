package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IDivisionDB;
import dhl.database.interfaceDB.ITeamDB;
import dhl.leagueModel.Division;
import dhl.leagueModel.interfaceModel.IDivision;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public List<IDivision> getDivisionList(int conferenceId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception{
        ITeamDB iTeamDB = databaseObjectCreationDB.getiTeamDB();
        List<IDivision> divisionList = new ArrayList<>();
        CallStoredProcedure callDivisionProc = new CallStoredProcedure("loadDivisions(?,?)");
        callDivisionProc.setParameter(1, conferenceId);
        callDivisionProc.setParameter(2, leagueId);
        ResultSet divisionsResultSet = callDivisionProc.executeWithResults();

        if (divisionsResultSet==null){
            throw new Exception("Error loading divisions");
        }

        while(divisionsResultSet.next()){
            IDivision division = new Division(divisionsResultSet.getString("divisionName"),
                    iTeamDB.getTeamList(divisionsResultSet.getInt("divisionId"),leagueId, databaseObjectCreationDB));
            divisionList.add(division);
        }
        callDivisionProc.cleanup();
        return divisionList;
    }
}
