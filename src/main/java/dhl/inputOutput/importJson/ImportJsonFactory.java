package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.ICreatedLeagueValidation;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.json.simple.JSONObject;

public class ImportJsonFactory extends ImportJsonAbstractFactory {


    public ICreateLeagueObjectModel createCreateLeagueObjectModel(JSONObject jsonLeagueObject) {
        return new CreateLeagueObjectModel(jsonLeagueObject);
    }

    public IImportJsonFile createImportJsonFile(String filePath) {
        return new ImportJsonFile(filePath);
    }

    public IJsonFilePath createJsonFilePath() {
        return new JsonFilePath();
    }

    public IUserInputOutput createUserInputOutput() {
        return new UserInputOutput();
    }

    public ICreatedLeagueValidation createdLeagueValidation(IValidation validation) {
        return new CreatedLeagueValidation(validation);
    }
}
