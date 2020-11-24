package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.inputOutput.importJson.interfaces.*;
import dhl.inputOutput.ui.IUserInputOutput;
import org.json.simple.JSONObject;

public abstract class ImportJsonAbstractFactory {
    private static ImportJsonAbstractFactory uniqueInstance = null;

    protected ImportJsonAbstractFactory(){

    }

    public static ImportJsonAbstractFactory instance(){
        if (null == uniqueInstance){
            uniqueInstance = new ImportJsonFactory();
        }
        return uniqueInstance;
    }

    public abstract ICheckInputFileFormat createCheckInputFileFormat();
    public abstract ICreateLeagueObjectModel createCreateLeagueObjectModel(JSONObject sonLeagueObject);
    public abstract IImportJsonFile createImportJsonFile(String filePath);
    public abstract IJsonFilePath createJsonFilePath();
    public abstract IUserInputOutput createUserInputOutput();
    public abstract ICreatedLeagueValidation createdLeagueValidation(IValidation validation);
}
