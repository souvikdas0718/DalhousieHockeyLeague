package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.leagueModel.interfaceModel.IConference;

import java.util.List;

public interface IConferenceDB {
    public int insertConference(String conferenceName, int leagudId) throws Exception;

    public List<IConference> getConferenceList(int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;
}
