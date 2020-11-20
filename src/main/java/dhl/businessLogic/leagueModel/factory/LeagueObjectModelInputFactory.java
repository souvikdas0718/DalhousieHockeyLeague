package dhl.businessLogic.leagueModel.factory;

import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.LeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public class LeagueObjectModelInputFactory {

    public ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam) {
        LeagueModelFactory leagueModelFactory = LeagueModelFactory.instance();
        ILeagueObjectModelValidation leagueObjectModelValidation = leagueModelFactory.createLeagueObjectModelValidation();
        // TODO: 19-11-2020 add object
        ISerializeLeagueObjectModel serializeLeagueObjectModel = null;
        return new LeagueObjectModelInput(leagueName, conferenceName, divisionName, newlyCreatedTeam, leagueObjectModelValidation, serializeLeagueObjectModel);
    }
}
