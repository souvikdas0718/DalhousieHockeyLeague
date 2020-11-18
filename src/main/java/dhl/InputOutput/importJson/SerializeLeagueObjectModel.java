package dhl.InputOutput.importJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.spi.protocol.ForwardException;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.simulationStateMachine.States.CreateTeamStateLogic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.logging.log4j.*;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SerializeLeagueObjectModel implements ISerializeLeagueObjectModel {
    Logger myLogger = LogManager.getLogger(CreateTeamStateLogic.class);

    public String serializeData(Object objLeagueObjectModel) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel, String filePath) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        File objFile = new File(filePath);
        if (objFile.exists()){
            myLogger.log(myLogger.getLevel(),"League Already exists");
        }else {
            if (objFile.createNewFile()) {
                writeJsonToFile(filePath, serializedLeagueObjectModel);
            } else {
                myLogger.log(myLogger.getLevel(),"Error saving league object data to json");
            }
        }
    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel, String filePath) throws IOException, Exception {
        String serializedLeagueObjectModel = serializeData(objLeagueObjectModel);
        File objFile = new File(filePath);
        if (objFile.exists()){
            writeJsonToFile(filePath, serializedLeagueObjectModel);
        }else {
            myLogger.log(myLogger.getLevel(),"This league doesn't exist");
        }
    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String filePath) throws IOException, Exception {
        String serializedplayers = serializeData(playersToRetire);
        File objFile = new File(filePath);
        if (objFile.exists()){
            updateJsonFile(filePath, serializedplayers);
        }else {
            objFile.createNewFile();
            writeJsonToFile(filePath, serializedplayers);
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

    public void updateJsonFile(String filePath, String newPlayers) throws IOException, ParseException {
        FileReader existingPlayers = new FileReader(filePath);
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

        writeJsonToFile(filePath, String.valueOf(arrCombined));
    }
}
