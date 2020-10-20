package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

import java.sql.Date;

public interface ILeagueDB {
    public int insertLeague(String leagueName) throws Exception;

    public void insertDate(int league_Id, int team_Id, Date season_startDate , Date current_date ) throws Exception;

    public boolean checkIfLeagueAlreadyExists(String leagueName) throws Exception;

}
