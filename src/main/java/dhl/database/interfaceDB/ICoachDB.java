package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.ICoach;

public interface ICoachDB {
    public int insertCoach(ICoach coach, int teamId, int leagueId )  throws Exception;
}
