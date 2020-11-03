package dhl.database.interfaceDB;

import dhl.database.DatabaseObjectCreationDB;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;

import java.util.List;

public interface IConferenceDB {
    int insertConference(String conferenceName, int leagudId) throws Exception;

    List<IConference> getConferenceList(int leagueId, DatabaseObjectCreationDB databaseObjectCreationDB) throws Exception;
}
