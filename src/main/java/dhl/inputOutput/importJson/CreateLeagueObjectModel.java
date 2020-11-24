package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.ICreatedLeagueValidation;
import dhl.inputOutput.ui.UserInputOutput;
import org.json.simple.JSONObject;


public class CreateLeagueObjectModel implements ICreateLeagueObjectModel {
    JSONObject jsonLeague;
    IValidation validation;
    ILeagueObjectModel leagueObjectModel;
    ILeagueObjectModelValidation leagueObjectModelValidation;
    UserInputOutput userInputOutput;
    ICreatedLeagueValidation createdLeagueValidation;

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject) {
        this.jsonLeague = jsonLeagueObject;
        LeagueModelAbstractFactory factory = LeagueModelAbstractFactory.instance();
        this.validation = factory.createCommonValidation();
        this.leagueObjectModelValidation = factory.createLeagueObjectModelValidation();
        ImportJsonAbstractFactory importFactory = ImportJsonAbstractFactory.instance();
        createdLeagueValidation = importFactory.createdLeagueValidation(validation);
        this.leagueObjectModel = null;
        userInputOutput= new UserInputOutput();
    }

    public ILeagueObjectModel getLeagueObjectModel() {
        ILeagueObjectModelBuilder leagueBuilder = new LeagueObjectModelBuilder();
        ILeagueObjectModelDirector leagueDirector = new LeagueObjectModelDirector(leagueBuilder);
        this.leagueObjectModel=leagueDirector.constructFromJson(jsonLeague);

        try {
            createdLeagueValidation.checkCreatedLeagueObjectModel(leagueObjectModel);
            leagueObjectModel.checkIfLeagueModelValid(validation, leagueObjectModelValidation);
        } catch (Exception e) {
            userInputOutput.printMessage(e.getMessage());
        }
        return leagueObjectModel;
    }

}
