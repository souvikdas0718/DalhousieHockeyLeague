package dhl.businessLogic.leagueModel.interfaceModel;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public interface ILeagueObjectModelInput {

    String getLeagueName();

    String getConferenceName();

    String getDivisionName();

    ITeam getNewlyCreatedTeam();

    ILeagueObjectModelValidation getLeagueObjectModelValidation();

    ISerializeLeagueObjectModel getSerializeLeagueObjectModel();

}