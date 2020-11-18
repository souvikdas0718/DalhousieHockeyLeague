package dhl.businessLogic.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;

public interface ILeagueObjectModelInput {

    public String getLeagueName();

    public String getConferenceName();

    public String getDivisionName();

    public ITeam getNewlyCreatedTeam();

    public ILeagueObjectModelValidation getLeagueObjectModelValidation();

    public ISerializeLeagueObjectModel getserializeLeagueObjectModel();

}
