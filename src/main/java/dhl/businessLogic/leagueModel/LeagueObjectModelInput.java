package dhl.businessLogic.leagueModel;

import dhl.inputOutput.importJson.interfaces.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public class LeagueObjectModelInput implements ILeagueObjectModelInput {
    private String leagueName;
    private String conferenceName;
    String divisionName;
    ITeam newlyCreatedTeam;
    ILeagueObjectModelValidation leagueObjectModelValidation;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;

    public LeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation, ISerializeLeagueObjectModel serializeLeagueObjectModel) {
        this.leagueName = leagueName;
        this.conferenceName = conferenceName;
        this.divisionName = divisionName;
        this.newlyCreatedTeam = newlyCreatedTeam;
        this.leagueObjectModelValidation = leagueObjectModelValidation;
        this.serializeLeagueObjectModel = serializeLeagueObjectModel;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public ITeam getNewlyCreatedTeam() { return newlyCreatedTeam; }

    public ILeagueObjectModelValidation getLeagueObjectModelValidation() {
        return leagueObjectModelValidation;
    }

    public ISerializeLeagueObjectModel getserializeLeagueObjectModel() {
        return serializeLeagueObjectModel;
    }
}
