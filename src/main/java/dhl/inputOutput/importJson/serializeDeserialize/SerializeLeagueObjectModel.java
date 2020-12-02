package dhl.inputOutput.importJson.serializeDeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SerializeLeagueObjectModel implements ISerializeLeagueObjectModel {
    final String playerFileName = "--RetiredPlayersInLeague.json";
    final String jsonExtension = ".json";
    Logger myLogger = LogManager.getLogger(SerializeLeagueObjectModel.class);
    String jsonFilePath;
    IUserInputOutput userInputOutput = IUserInputOutput.getInstance();

    public SerializeLeagueObjectModel(String inputJsonFilePath) {
        jsonFilePath = inputJsonFilePath;
    }

    public String serializeData(Object objLeagueObjectModel) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void writeJsonToFile(String filePath, String serializedData) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(serializedData);
        } finally {
            fileWriter.close();
        }
    }

    public Boolean writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;

        File objFile = new File(leagueObjectModelJsonPath);

        if (objFile.exists()) {
            userInputOutput.printMessage("League Already exists");
            myLogger.info("League Already exists");
        } else {
            if (objFile.createNewFile()) {
                writeJsonToFile(leagueObjectModelJsonPath, serializedLeagueObjectModel);
            }
        }
        return true;
    }

    public Boolean updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;

        File objFile = new File(leagueObjectModelJsonPath);
        if (objFile.exists()) {
            writeJsonToFile(leagueObjectModelJsonPath, serializedLeagueObjectModel);
        } else {
            userInputOutput.printMessage("This league doesn't exist");
            myLogger.info("This league doesn't exist");
        }
        return true;
    }

    public Boolean updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName) throws IOException, ParseException {
        String playersJsonPath = jsonFilePath + leagueName + playerFileName;

        String serializedplayers = null;

        serializedplayers = serializeData(playersToRetire);
        File objFile = new File(playersJsonPath);
        if (objFile.exists()) {
            updateJsonFile(serializedplayers, playersJsonPath);
        } else {
            objFile.createNewFile();
            writeJsonToFile(playersJsonPath, serializedplayers);
        }
        return true;
    }

    public void updateJsonFile(String newPlayers, String playersJsonPath) throws IOException, ParseException {
        FileReader existingPlayers = null;
        try {
            existingPlayers = new FileReader(playersJsonPath);
            JSONParser jsonParser = new JSONParser();
            JSONArray arrExistingPlayers = (JSONArray) jsonParser.parse(existingPlayers);
            JSONArray arrNewPlayers = (JSONArray) jsonParser.parse(newPlayers);
            JSONArray arrCombined = new JSONArray();

            Iterator<?> existingPlayersIterator = (arrNewPlayers).iterator();
            while (existingPlayersIterator.hasNext()) {
                JSONObject existingPlayersJsonObject = (JSONObject) existingPlayersIterator.next();
                arrCombined.add(existingPlayersJsonObject);
            }

            Iterator<?> newPlayersIterator = (arrExistingPlayers).iterator();
            while (newPlayersIterator.hasNext()) {
                JSONObject newPlayersJsonObject = (JSONObject) newPlayersIterator.next();
                arrCombined.add(newPlayersJsonObject);
            }

            writeJsonToFile(playersJsonPath, String.valueOf(arrCombined));
        } finally {
            existingPlayers.close();
        }
    }
}
