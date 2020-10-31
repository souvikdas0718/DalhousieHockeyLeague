package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.leagueModel.interfaceModel.ITeam;

public class LeagueObjectModelInput implements ILeagueObjectModelInput {
    private String leagueName;
    private String conferenceName;
    String divisionName;
    ITeam newlyCreatedTeam;
    ILeagueObjectModelValidation leagueObjectModelValidation;

    public LeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation){
        this.leagueName=leagueName;
        this.conferenceName=conferenceName;
        this.divisionName=divisionName;
        this.newlyCreatedTeam=newlyCreatedTeam;
        this.leagueObjectModelValidation=leagueObjectModelValidation;
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

    public ITeam getNewlyCreatedTeam() {
        return newlyCreatedTeam;
    }

    public ILeagueObjectModelValidation getLeagueObjectModelValidation() {
        return leagueObjectModelValidation;
    }

}
