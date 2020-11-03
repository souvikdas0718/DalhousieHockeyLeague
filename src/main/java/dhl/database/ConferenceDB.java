package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IConferenceDB;
import dhl.database.interfaceDB.IDivisionDB;
import dhl.businessLogic.leagueModel.Conference;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDB implements IConferenceDB {

    public int insertConference(String conferenceName, int leagudId) throws Exception {
        int conferenceId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertConference(?,?)");
        callproc.setParameter(1, leagudId);
        callproc.setParameter(2, conferenceName);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Conference not inserted properly");
        }

        while (results.next()) {
            conferenceId = results.getInt("conference_Id");
        }

        callproc.cleanup();
        return conferenceId;
    }

    public List<IConference> getConferenceList(int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception {
        IDivisionDB divisionDB = databaseObjectCreationDB.getDivisionDB();
        List<IConference> conferencesList = new ArrayList<>();
        CallStoredProcedure callConfProc = new CallStoredProcedure("loadConferences(?)");
        callConfProc.setParameter(1, leagueId);
        ResultSet conferencesResultSet = callConfProc.executeWithResults();

        if (conferencesResultSet == null) {
            throw new Exception("Error loading conferences");
        }

        while (conferencesResultSet.next()) {
            IConference conference = new Conference(conferencesResultSet.getString("conferenceName"),
                    divisionDB.getDivisionList(conferencesResultSet.getInt("conferenceId"), leagueId, databaseObjectCreationDB));
            conferencesList.add(conference);
        }
        callConfProc.cleanup();
        return conferencesList;
    }
}
