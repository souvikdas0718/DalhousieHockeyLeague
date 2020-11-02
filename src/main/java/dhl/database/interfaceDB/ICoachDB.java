package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.ICoach;

import java.util.List;

public interface ICoachDB {
    int insertCoach(ICoach coach, int teamId, int leagueId) throws Exception;

    void updateCoach(ICoach coach, String teamName, String leagueName) throws Exception;

    int insertUnassignedCoach(ICoach coach, int leagueId) throws Exception;

    ICoach getTeamCoach(int teamId, int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;

    List<ICoach> getUnassignedCoachList(int leagueId) throws Exception;
}
