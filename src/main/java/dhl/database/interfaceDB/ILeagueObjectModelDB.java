package dhl.database.interfaceDB;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueObjectModelDB {

    void insertLeagueModel(ILeagueObjectModel conferences) throws Exception;

    ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) throws Exception;

    void updateLeagueModel(ILeagueObjectModel leagueObjectModel) throws Exception;
}
