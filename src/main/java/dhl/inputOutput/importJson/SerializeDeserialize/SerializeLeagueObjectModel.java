package dhl.inputOutput.importJson.serializeDeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.simulationStateMachine.states.CreateTeamStateLogic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.logging.log4j.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SerializeLeagueObjectModel implements ISerializeLeagueObjectModel {
    Logger myLogger = LogManager.getLogger(CreateTeamStateLogic.class);
    String jsonFilePath;
    final String playerFileName = "--InjuredPlayer.json";
    final String jsonExtension = ".json";

    public SerializeLeagueObjectModel(String jsonFilePath){
        jsonFilePath = jsonFilePath;
    }

    public String serializeData(Object objLeagueObjectModel) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void writeJsonToFile(String filePath, String serializedData) throws IOException {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(filePath);
            fileWriter.write(serializedData);
        }
        finally {
            fileWriter.close();
        }
    }

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;

        File objFile = new File(jsonFilePath);
        if (objFile.exists()){
            myLogger.log(myLogger.getLevel(),"League Already exists");
        }else {
            if (objFile.createNewFile()) {
                writeJsonToFile(jsonFilePath, serializedLeagueObjectModel);
            } else {
                myLogger.log(myLogger.getLevel(),"Error saving league object data to json");
            }
        }
    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        String leagueObjectModelJsonPath = jsonFilePath + objLeagueObjectModel.getLeagueName() + jsonExtension;

        File objFile = new File(jsonFilePath);
        if (objFile.exists()){
            writeJsonToFile(jsonFilePath, serializedLeagueObjectModel);
        }else {
            myLogger.log(myLogger.getLevel(),"This league doesn't exist");
        }
    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName) throws IOException, Exception {
        String playersJsonPath = jsonFilePath + leagueName + playerFileName;

        String serializedplayers = serializeData(playersToRetire);
        File objFile = new File(jsonFilePath);
        if (objFile.exists()){
            updateJsonFile(serializedplayers, playersJsonPath);
        }else {
            objFile.createNewFile();
            writeJsonToFile(jsonFilePath, serializedplayers);
        }
    }

    public void updateJsonFile(String newPlayers, String playersJsonPath) throws IOException, ParseException {
        FileReader existingPlayers = new FileReader(playersJsonPath);
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
    }
}
