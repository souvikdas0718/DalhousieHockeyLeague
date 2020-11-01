package dhl.leagueModel.interfaceModel;

import dhl.database.interfaceDB.ILeagueObjectModelDB;

public interface ILeagueObjectModelInput {

    public String getLeagueName();

    public String getConferenceName();

    public String getDivisionName();

    public ITeam getNewlyCreatedTeam();

    public ILeagueObjectModelValidation getLeagueObjectModelValidation();

    public ILeagueObjectModelDB getLeagueObjectModelDB();

}
