package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.ICreatedLeagueValidation;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.json.simple.JSONObject;

public abstract class ImportJsonAbstractFactory {
    private static ImportJsonAbstractFactory uniqueInstance = null;

    protected ImportJsonAbstractFactory() {

    }

    public static ImportJsonAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new ImportJsonFactory();
        }
        return uniqueInstance;
    }

    public abstract ICreateLeagueObjectModel createCreateLeagueObjectModel(JSONObject sonLeagueObject);

    public abstract IImportJsonFile createImportJsonFile(String filePath);

    public abstract IJsonFilePath createJsonFilePath();

    public abstract IUserInputOutput createUserInputOutput();

    public abstract ICreatedLeagueValidation createdLeagueValidation(IValidation validation);
}
