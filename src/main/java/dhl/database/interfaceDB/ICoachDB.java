package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.ICoach;

import java.util.List;

public interface ICoachDB {
    public int insertCoach(ICoach coach, int teamId, int leagueId )  throws Exception;

    public void updateCoach(ICoach coach, String teamName, String leagueName)  throws Exception;

    public int insertUnassignedCoach(ICoach coach, int leagueId )  throws Exception;

    public ICoach getTeamCoach(int teamId,int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;

    public List<ICoach> getUnassignedCoachList(int leagueId) throws Exception;
}
