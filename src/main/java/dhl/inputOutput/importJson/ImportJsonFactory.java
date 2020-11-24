package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.inputOutput.importJson.interfaces.ICheckInputFileFormat;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
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
}
