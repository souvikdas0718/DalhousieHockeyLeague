package dhl.importJsonTest;

import dhl.inputOutput.importJson.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.SerializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SerializeLeagueObjectModelTest {
    private static final String filepath = "src/test/java/dhl/importJsonTest/";
    private static final String extension = ".json";

    SerializeLeagueObjectModel serializeLeagueobjectModel;
    IDeserializeLeagueObjectModel deserializeLeagueobjectModel;
    LeagueObjectModelMocks mockLeagueObjectModel;
    SerializedJsonMock mockSerializedJson;
    String filePath;

    @BeforeEach
    public void initObject() {
        serializeLeagueobjectModel = new SerializeLeagueObjectModel();
        deserializeLeagueobjectModel = new DeserializeLeagueObjectModel();
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        mockSerializedJson = new SerializedJsonMock();
    }

    @Test
    void serializeLeagueObjectModelTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();

        String serializedleagueModel = serializeLeagueobjectModel.serializeData(mockLeagueObjectModel.leagueModelMock());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(serializedleagueModel);

        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson(jsonLeagueObject);

        Assertions.assertEquals(serializedleagueModel, mockSerializedJson.serializedJson());
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
        Assertions.assertEquals(2, leagueObjectModel.getConferences().size());
        Assertions.assertEquals(2, leagueObjectModel.getFreeAgents().size());
        Assertions.assertEquals(2, leagueObjectModel.getCoaches().size());
        Assertions.assertEquals(2, leagueObjectModel.getGeneralManagers().size());
    }

    @Test
    void WriteSerializedLeagueObjectToFileTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        String jsonFilePath = filepath + "TestleagueName" + extension;
        File objFile = new File(jsonFilePath);
        if(objFile.exists()) {
            objFile.delete();
        }

        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMock(),jsonFilePath);

        FileReader leagueObjectJson = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJson);
        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson(objLeagueObject);
        Assertions.assertNotNull(leagueObjectModel);
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    void UpdateSerializedLeagueObjectToJsonFileTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        String jsonFilePath = filepath + "TestleagueName" + extension;
        File objFile = new File(jsonFilePath);

        serializeLeagueobjectModel.updateSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMock(),jsonFilePath);

        FileReader leagueObjectJson = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJson);
        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson(objLeagueObject);
        Assertions.assertNotNull(leagueObjectModel);
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    void updateSerializedPlayerListToJsonFile() throws Exception {
        List<IPlayer> playersList = new ArrayList<>();
        String jsonFilePath = filepath + "TestPlayerList" + extension;
        File objFile = new File(jsonFilePath);
        if(objFile.exists()) {
            objFile.delete();
        }
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 5, 5, 5, 5);
        players.add(new Player("Henry", "forward", false, playerStatistics1));

        serializeLeagueobjectModel.updateSerializedPlayerListToJsonFile(players,jsonFilePath);

        FileReader playersJson = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONArray objPlayersObject = (JSONArray) jsonParser.parse(playersJson);
        playersList = deserializeLeagueobjectModel.deserializePlayers(objPlayersObject);
        Assertions.assertNotNull(playersList);
        Assertions.assertEquals(1, playersList.size());
    }

    @Test
    void writeJsonToFileTest() throws IOException {
        String jsonFilePath = filepath + "TestPlayerList" + extension;
        File objFile = new File(jsonFilePath);
        if(objFile.exists()) {
            objFile.delete();
        }
        serializeLeagueobjectModel.writeJsonToFile(jsonFilePath,mockSerializedJson.serializedPlayerList());
        FileReader playersJson = new FileReader(jsonFilePath);
        Assertions.assertNotNull(playersJson);
    }

    @Test
    void updateJsonFileTest() throws IOException, ParseException {
        String jsonFilePath = filepath + "TestPlayerList" + extension;
        File objFile = new File(jsonFilePath);
        if(objFile.exists()) {
            objFile.delete();
        }
        serializeLeagueobjectModel.updateJsonFile(jsonFilePath,mockSerializedJson.serializedPlayerList());
        FileReader playersJson = new FileReader(jsonFilePath);
        Assertions.assertNotNull(playersJson);
    }
}
