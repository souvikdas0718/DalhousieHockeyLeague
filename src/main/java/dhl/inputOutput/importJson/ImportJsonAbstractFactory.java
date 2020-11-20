package dhl.inputOutput.importJson;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.businessLogic.traning.TrainingFactory;
import dhl.businessLogic.traning.interfaces.ITraining;
import dhl.inputOutput.importJson.interfaces.ICheckInputFileFormat;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
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
    public abstract ICreateLeagueObjectModel createCreateLeagueObjectModel();
    public abstract IGameConfig createGameConfig(JSONObject jsonObject);
    public abstract void createImportJsonFile(String filePath);
    public abstract IJsonFilePath createJsonFilePath();
}
