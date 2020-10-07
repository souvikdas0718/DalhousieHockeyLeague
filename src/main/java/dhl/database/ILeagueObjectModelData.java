package dhl.database;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueObjectModelData {
    public void insertLeagueModel(ILeagueObjectModel leagueObjectModel);
    public ILeagueObjectModel loadTeam(String leagueName,String conferenceName,String divisionName,String teamName);
}
