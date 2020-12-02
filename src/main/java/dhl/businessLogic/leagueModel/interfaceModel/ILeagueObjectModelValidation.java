package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.List;

public interface ILeagueObjectModelValidation {

    boolean checkIfLeagueObjectModelValid(IValidation validation, ILeagueObjectModel leagueObjectModel);

    boolean checkIfLeagueDetailsValid(List<IConference> conferences);

    boolean checkUserInputForLeague(ILeagueObjectModel leagueObjectModel, ILeagueObjectModelInput leagueObjectModelInput);
}
