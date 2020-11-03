package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ICoachDB;
import dhl.businessLogic.leagueModel.Coach;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachDB implements ICoachDB {
    public int insertCoach(ICoach coach, int teamId, int leagueId) throws Exception {
        int coachId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertCoach(?,?,?,?,?,?,?)");
        callproc.setParameter(1, coach.getCoachName());
        callproc.setParameter(2, coach.getSkating());
        callproc.setParameter(3, coach.getShooting());
        callproc.setParameter(4, coach.getChecking());
        callproc.setParameter(5, coach.getSaving());
        callproc.setParameter(6, teamId);
        callproc.setParameter(7, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (null == results) {
            throw new Exception("Coach not inserted properly");
        } else {
            while (results.next()) {
                coachId = results.getInt("coach_Id");
            }
        }
        callproc.cleanup();
        return coachId;
    }

    public void updateCoach(ICoach coach, String teamName, String leagueName) throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("updateCoach(?,?,?,?,?,?,?)");
            callproc.setParameter(1, coach.getCoachName());
            callproc.setParameter(2, coach.getSkating());
            callproc.setParameter(3, coach.getShooting());
            callproc.setParameter(4, coach.getChecking());
            callproc.setParameter(5, coach.getSaving());
            callproc.setParameter(6, teamName);
            callproc.setParameter(7, leagueName);
            callproc.execute();
            callproc.cleanup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int insertUnassignedCoach(ICoach coach, int leagueId) throws Exception {
        int coachId = 0;

        CallStoredProcedure callproc = new CallStoredProcedure("`insertUnassignedCoaches`(?,?,?,?,?,?)");
        callproc.setParameter(1, coach.getCoachName());
        callproc.setParameter(2, coach.getSkating());
        callproc.setParameter(3, coach.getShooting());
        callproc.setParameter(4, coach.getChecking());
        callproc.setParameter(5, coach.getSaving());
        callproc.setParameter(6, leagueId);
        ResultSet results = callproc.executeWithResults();

        if (results == null) {
            throw new Exception("Coach not inserted properly");
        }

        while (results.next()) {
            coachId = results.getInt("coach_Id");
        }

        callproc.cleanup();

        return coachId;
    }

    public ICoach getTeamCoach(int teamId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception {
        ICoach headCoach = null;
        CallStoredProcedure callCoachProc = new CallStoredProcedure("loadCoach(?,?)");
        callCoachProc.setParameter(1, teamId);
        callCoachProc.setParameter(2, leagueId);
        ResultSet coachResultSet = callCoachProc.executeWithResults();

        if (coachResultSet == null) {
            throw new Exception("Error loading coach");
        }

        while (coachResultSet.next()) {
            headCoach = new Coach(coachResultSet.getString("coachName"), coachResultSet.getInt("skating"),
                    coachResultSet.getInt("shooting"), coachResultSet.getInt("checking"), coachResultSet.getInt("saving"));
        }
        callCoachProc.cleanup();
        return headCoach;
    }

    public List<ICoach> getUnassignedCoachList(int leagueId) throws Exception {
        List<ICoach> coaches = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadUnassignedCoaches(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet coachResultSet = callAgentProc.executeWithResults();

        if (coachResultSet == null) {
            throw new Exception("Error loading Coaches List");
        }

        while (coachResultSet.next()) {
            ICoach headCoach = new Coach(coachResultSet.getString("coachName"), coachResultSet.getInt("skating"),
                    coachResultSet.getInt("shooting"), coachResultSet.getInt("checking"), coachResultSet.getInt("saving"));
            coaches.add(headCoach);
        }
        callAgentProc.cleanup();
        return coaches;
    }
}
