package dhl.inputOutput.importJson;

import dhl.inputOutput.importJson.interfaces.ICheckInputFileFormat;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import org.json.simple.JSONObject;

public class ImportJsonFactory extends ImportJsonAbstractFactory {
    public ICheckInputFileFormat createCheckInputFileFormat() {
        return new CheckInputFileFormat();
    }

    public ICreateLeagueObjectModel createCreateLeagueObjectModel() {
        return new CreateLeagueObjectModel();
    }

    public IGameConfig createGameConfig(JSONObject jsonObject) {
        return new GameConfig(jsonObject);
    }

    public void createImportJsonFile(String filePath) {

    }

    public IJsonFilePath createJsonFilePath() {
        return new JsonFilePath();
    }
}
