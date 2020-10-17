package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;

import java.sql.ResultSet;

public class ConferenceDB implements IConferenceDB {
    public int insertConference(String conferenceName, int leagudId) throws Exception{
        int conferenceId=0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertConference(?,?)");
        callproc.setParameter(1, leagudId);
        callproc.setParameter(2, conferenceName);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while(results.next()) {
                conferenceId = results.getInt("conference_Id");
            }
        }
        else {
            throw new Exception("Conference not inserted properly");
        }

        callproc.cleanup();

        return conferenceId;
    }

}
