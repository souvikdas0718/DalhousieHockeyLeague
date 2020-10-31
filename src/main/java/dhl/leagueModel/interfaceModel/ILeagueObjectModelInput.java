package dhl.leagueModel.interfaceModel;

public interface ILeagueObjectModelInput {

    public String getLeagueName();

    public String getConferenceName();

    public String getDivisionName();

    public ITeam getNewlyCreatedTeam();

    public ILeagueObjectModelValidation getLeagueObjectModelValidation();

}
