package dhl.inputOutput.importJson.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;

public interface ICreatedLeagueValidation {

    boolean checkCreatedLeagueObjectModel(ILeagueObjectModel leagueObjectModel);
}
