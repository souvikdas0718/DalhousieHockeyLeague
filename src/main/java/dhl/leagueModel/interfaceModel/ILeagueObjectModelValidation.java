package dhl.leagueModel.interfaceModel;

import java.util.List;

public interface ILeagueObjectModelValidation {
    public boolean checkIfLeagueObjectModelValid(IValidation validation,ILeagueObjectModel leagueObjectModel) throws Exception;

    public void checkIfLeagueDetailsValid(List<IConference> conferences) throws Exception;

    public boolean checkUserInputForLeague(ILeagueObjectModel leagueObjectModel, ILeagueObjectModelInput leagueObjectModelInput)throws Exception;
}
