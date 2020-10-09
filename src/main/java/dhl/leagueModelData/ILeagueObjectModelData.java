package dhl.leagueModelData;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueObjectModelData {

    public void insertLeagueModel(ILeagueObjectModel conferences) throws Exception;

    public boolean checkIfLeagueAlreadyExists(String leagueName);

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName);

    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) throws Exception;
}
