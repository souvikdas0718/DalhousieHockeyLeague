package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ICoachDB;
import dhl.leagueModel.interfaceModel.ICoach;

import java.sql.ResultSet;

public class CoachDB implements ICoachDB {
    public int insertCoach(ICoach coach, int teamId, int leagueId )  throws Exception {
        int coachId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertCoach(?,?,?,?,?,?,?)");
        callproc.setParameter(1, coach.getCoachName());
        callproc.setParameter(2, coach.getSkating());
        callproc.setParameter(3, coach.getShooting());
        callproc.setParameter(4, coach.getChecking());
        callproc.setParameter(5, coach.getSaving());
        callproc.setParameter(6, teamId);
        callproc.setParameter(7, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                coachId = results.getInt("coach_Id");
            }
        }
        else {
            throw new Exception("Coach not inserted properly");
        }

        callproc.cleanup();

        return coachId;
    }

    public int insertUnassignedCoach(ICoach coach, int leagueId )  throws Exception {
        int coachId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("`insertUnassignedCoaches`(?,?,?,?,?,?)");
        callproc.setParameter(1, coach.getCoachName());
        callproc.setParameter(2, coach.getSkating());
        callproc.setParameter(3, coach.getShooting());
        callproc.setParameter(4, coach.getChecking());
        callproc.setParameter(5, coach.getSaving());
        callproc.setParameter(6, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null != results) {
            while (results.next()) {
                coachId = results.getInt("coach_Id");
            }
        }
        else {
            throw new Exception("Coach not inserted properly");
        }

        callproc.cleanup();

        return coachId;
    }
}
