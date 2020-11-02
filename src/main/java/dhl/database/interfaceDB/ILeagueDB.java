package dhl.database.interfaceDB;

import java.sql.Date;

public interface ILeagueDB {
    int insertLeague(String leagueName) throws Exception;

    void insertDate(int league_Id, int team_Id, Date season_startDate, Date current_date) throws Exception;

    boolean checkIfLeagueAlreadyExists(String leagueName) throws Exception;

}
