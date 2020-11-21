package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.ui.UserInputOutput;
import org.json.simple.JSONObject;

public class CreateLeagueObjectModel implements ICreateLeagueObjectModel {
    JSONObject jsonLeague;
    IValidation validationObject;
    ILeagueObjectModel leagueObjectModel;
    ILeagueObjectModelValidation leagueObjectModelValidation;
    UserInputOutput userInputOutput;

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject) {
        this.jsonLeague = jsonLeagueObject;
        LeagueModelAbstractFactory factory = LeagueModelAbstractFactory.instance();
        this.validationObject = factory.createCommonValidation();
        this.leagueObjectModelValidation = factory.createLeagueObjectModelValidation();
        this.leagueObjectModel = null;
        userInputOutput= new UserInputOutput();
    }

    public ILeagueObjectModel getLeagueObjectModel() {
        ILeagueObjectModelBuilder leagueBuilder = new LeagueObjectModelBuilder();
        ILeagueObjectModelDirector leagueDirector = new LeagueObjectModelDirector(leagueBuilder);
        this.leagueObjectModel=leagueDirector.constructFromJson(jsonLeague);

        try {
            leagueObjectModel.checkIfLeagueModelValid(validationObject, leagueObjectModelValidation);
        } catch (Exception e) {
            userInputOutput.printMessage(e.getMessage());
        }
        return leagueObjectModel;
    }

}
