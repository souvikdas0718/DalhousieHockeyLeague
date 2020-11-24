package dhl.inputOutput.importJson.serializeDeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
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

    public SerializeLeagueObjectModel(String inputJsonFilePath) {
        jsonFilePath = inputJsonFilePath;
    }

    public String serializeData(Object objLeagueObjectModel)  {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void writeJsonToFile(String filePath, String serializedData)  {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(serializedData);
        } catch (IOException ioException) {
            myLogger.error("Write Json to file while serializing file");
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ioException) {
                myLogger.error("IO Exception while writing JSON to file");
            }
        }
    }

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;
        try {
        File objFile = new File(leagueObjectModelJsonPath);

        if (objFile.exists()) {
            myLogger.log(myLogger.getLevel(), "League Already exists");
        } else {
            if (objFile.createNewFile()) {
                writeJsonToFile(leagueObjectModelJsonPath, serializedLeagueObjectModel);
            } else {
                myLogger.log(myLogger.getLevel(), "Error saving league object data to json");

            }
        }
        } catch (IOException ioException) {
            myLogger.error("IO Exception while writing to file"+leagueObjectModelJsonPath);
        }
    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;

        File objFile = new File(leagueObjectModelJsonPath);
        if (objFile.exists()) {
            writeJsonToFile(leagueObjectModelJsonPath, serializedLeagueObjectModel);
        } else {
            myLogger.log(myLogger.getLevel(), "This league doesn't exist");
        }
    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName)  {
        String playersJsonPath = jsonFilePath + leagueName + playerFileName;

        String serializedplayers = null;
        try {
            serializedplayers = serializeData(playersToRetire);
            File objFile = new File(playersJsonPath);
            if (objFile.exists()) {
                updateJsonFile(serializedplayers, playersJsonPath);
            } else {
                objFile.createNewFile();
                writeJsonToFile(playersJsonPath, serializedplayers);
            }
        } catch (Exception exception) {
            myLogger.error(" Exception while serializing players file");
        }
    }

    public void updateJsonFile(String newPlayers, String playersJsonPath) {
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
        } catch (IOException ioException){
            myLogger.error("IO Exception while serializing file");
        } catch (ParseException parseException){
            myLogger.error("Parse Exception while serializing file");
        } finally {
            try {
                existingPlayers.close();
            }catch (IOException e){
                myLogger.error("IO Exception while closing file");
            }

        }
    }
}
