package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;

public interface ILeagueObjectModelValidation {
    public boolean checkIfLeagueObjectModelValid(IValidation validation,ILeagueObjectModel leagueObjectModel) throws Exception;

    public void checkIfLeagueDetailsValid(ArrayList<IConference> conferences) throws Exception;

    public boolean checkUserInputForLeague(ILeagueObjectModel leagueObjectModel, String leagueName, String conferenceName, String divisionName, String teamName)throws Exception;
}
