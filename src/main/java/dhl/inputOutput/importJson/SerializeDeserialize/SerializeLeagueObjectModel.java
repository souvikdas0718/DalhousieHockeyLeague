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
    String leagueObjectModelFilePath;
    String playersFilePath;

    public SerializeLeagueObjectModel(String leagueName){
        leagueObjectModelFilePath = "src/main/java/dhl/inputOutput/importJson/serializeDeserialize/serializedJsonFiles/" + leagueName + ".json";
        playersFilePath = "src/main/java/dhl/inputOutput/importJson/serializeDeserialize/serializedJsonFiles/injuredPlayers" + leagueName + ".json";
    }

    public String serializeData(Object objLeagueObjectModel) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        File objFile = new File(leagueObjectModelFilePath);
        if (objFile.exists()){
            myLogger.log(myLogger.getLevel(),"League Already exists");
        }else {
            if (objFile.createNewFile()) {
                writeJsonToFile(leagueObjectModelFilePath, serializedLeagueObjectModel);
            } else {
                myLogger.log(myLogger.getLevel(),"Error saving league object data to json");
            }
        }
    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        File objFile = new File(leagueObjectModelFilePath);
        if (objFile.exists()){
            writeJsonToFile(leagueObjectModelFilePath, serializedLeagueObjectModel);
        }else {
            myLogger.log(myLogger.getLevel(),"This league doesn't exist");
        }
    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire) throws IOException, Exception {
        String serializedplayers = serializeData(playersToRetire);
        File objFile = new File(playersFilePath);
        if (objFile.exists()){
            updateJsonFile(serializedplayers);
        }else {
            objFile.createNewFile();
            writeJsonToFile(playersFilePath, serializedplayers);
        }
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

    public void updateJsonFile(String newPlayers) throws IOException, ParseException {
        FileReader existingPlayers = new FileReader(playersFilePath);
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

        writeJsonToFile(playersFilePath, String.valueOf(arrCombined));
    }
}
