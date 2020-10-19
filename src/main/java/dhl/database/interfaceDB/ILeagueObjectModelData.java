package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueObjectModelData {

    public void insertLeagueModel(ILeagueObjectModel conferences) throws Exception;

    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) throws Exception;
}
