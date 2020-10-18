package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.ICoachDB;
import dhl.leagueModel.interfaceModel.ICoach;

import java.sql.ResultSet;

public class CoachDB implements ICoachDB {
    public int insertCoach(ICoach coach, int teamId, int leagueId )  throws Exception {
        int playerId =0;

        CallStoredProcedure callproc = new CallStoredProcedure("insertCoach(?,?,?,?,?,?,?,)");
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
                playerId = results.getInt("player_Id");
            }
        }
        else {
            throw new Exception("Player not inserted properly");
        }

        callproc.cleanup();

        return playerId;
    }
}
