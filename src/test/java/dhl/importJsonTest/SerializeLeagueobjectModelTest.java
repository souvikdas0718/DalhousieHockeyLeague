package dhl.importJsonTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.inputOutput.importJson.serializeDeserialize.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SerializeLeagueobjectModelTest {
    private static final String filepath = "src/test/java/dhl/Mocks/";
    private static final String extension = ".json";

    SerializeLeagueObjectModel serializeLeagueobjectModel;
    IDeserializeLeagueObjectModel deserializeLeagueobjectModel;
    LeagueObjectModelMocks mockLeagueObjectModel;
    SerializedJsonMock mockSerializedJson;
    String filePath;

    @BeforeEach
    public void initObject() {
        serializeLeagueobjectModel = new SerializeLeagueObjectModel(filepath);
        deserializeLeagueobjectModel = new DeserializeLeagueObjectModel(filepath);
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        mockSerializedJson = new SerializedJsonMock();
    }

    @Test
    void serializeLeagueObjectModelTest() throws Exception {
//        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
//
//        String serializedleagueModel = serializeLeagueobjectModel.serializeData(mockLeagueObjectModel.leagueModelMock());
//
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(serializedleagueModel);
//
//        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("Dhl");
//
//        Assertions.assertEquals(serializedleagueModel, mockSerializedJson.serializedJson());
//        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
//        Assertions.assertEquals(2, leagueObjectModel.getConferences().size());
//        Assertions.assertEquals(2, leagueObjectModel.getFreeAgents().size());
//        Assertions.assertEquals(2, leagueObjectModel.getCoaches().size());
//        Assertions.assertEquals(2, leagueObjectModel.getGeneralManagers().size());
    }

    @Test
    void WriteSerializedLeagueObjectToFileTest() throws Exception {
//        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
//        String jsonFilePath = filepath + "TestleagueName" + extension;
//        File objFile = new File(jsonFilePath);
//        if(objFile.exists()) {
//            objFile.delete();
//        }
//
//        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMock());
//
//        FileReader leagueObjectJson = new FileReader(jsonFilePath);
//        JSONParser jsonParser = new JSONParser();
//        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJson);
//        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("");
//        Assertions.assertNotNull(leagueObjectModel);
//        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    void UpdateSerializedLeagueObjectToJsonFileTest() throws Exception {
//        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
//        String jsonFilePath = filepath + "TestleagueName" + extension;
//        File objFile = new File(jsonFilePath);
//
//        serializeLeagueobjectModel.updateSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMock());
//
//        FileReader leagueObjectJson = new FileReader(jsonFilePath);
//        JSONParser jsonParser = new JSONParser();
//        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJson);
//        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("");
//        Assertions.assertNotNull(leagueObjectModel);
//        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    void updateSerializedPlayerListToJsonFile() throws Exception {
//        List<IPlayer> playersList = new ArrayList<>();
//        String jsonFilePath = filepath + "TestPlayerList" + extension;
//        File objFile = new File(jsonFilePath);
//        if(objFile.exists()) {
//            objFile.delete();
//        }
//        List<IPlayer> players = new ArrayList<>();
//        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 5, 5, 5, 5);
//        players.add(new Player("Henry", "forward", false, playerStatistics1));
//
//        serializeLeagueobjectModel.updateSerializedPlayerListToJsonFile(players, "");
//
//        FileReader playersJson = new FileReader(jsonFilePath);
//        JSONParser jsonParser = new JSONParser();
//        JSONArray objPlayersObject = (JSONArray) jsonParser.parse(playersJson);
//        playersList = deserializeLeagueobjectModel.deserializePlayers("");
//        Assertions.assertNotNull(playersList);
//        Assertions.assertEquals(1, playersList.size());
    }

    @Test
    void writeJsonToFileTest() throws IOException {
        String jsonFilePath = filepath + "TestPlayerList" + extension;
//        File objFile = new File(jsonFilePath);
//        if(objFile.exists()) {
//            objFile.delete();
//        }
//        serializeLeagueobjectModel.writeJsonToFile(jsonFilePath,mockSerializedJson.serializedPlayerList());
//        FileReader playersJson = new FileReader(jsonFilePath);
//        Assertions.assertNotNull(playersJson);
    }

    @Test
    void updateJsonFileTest() throws IOException, ParseException {
//        String jsonFilePath = filepath + "TestPlayerList" + extension;
//        File objFile = new File(jsonFilePath);
//        if(objFile.exists()) {
//            objFile.delete();
//        }
//        serializeLeagueobjectModel.updateJsonFile(mockSerializedJson.serializedPlayerList(),"");
//        FileReader playersJson = new FileReader(jsonFilePath);
//        Assertions.assertNotNull(playersJson);
    }
}
