package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.inputOutput.importJson.interfaces.*;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;
import org.json.simple.JSONObject;

public class ImportJsonFactory extends ImportJsonAbstractFactory {
    public ICheckInputFileFormat createCheckInputFileFormat() {
        return new CheckInputFileFormat();
    }

    public ICreateLeagueObjectModel createCreateLeagueObjectModel(JSONObject jsonLeagueObject) {
        return new CreateLeagueObjectModel(jsonLeagueObject);
    }

    public IImportJsonFile createImportJsonFile(String filePath) {
        return new ImportJsonFile(filePath);

    }

    public IJsonFilePath createJsonFilePath() {
        return new JsonFilePath();
    }

    public IUserInputOutput createUserInputOutput(){
        return new UserInputOutput();
    }

    public ICreatedLeagueValidation createdLeagueValidation(IValidation validation){
        return new CreatedLeagueValidation(validation);
    }
}
