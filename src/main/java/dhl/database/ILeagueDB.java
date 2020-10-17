package dhl.database;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueDB {
    public int insertLeague(String leagueName) throws Exception;

    public boolean checkIfLeagueAlreadyExists(String leagueName) throws Exception;

}
