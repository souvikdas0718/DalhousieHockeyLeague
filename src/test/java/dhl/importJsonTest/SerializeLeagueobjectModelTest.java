package dhl.importJsonTest;

import dhl.mocks.LeagueObjectModelMocks;
import dhl.importJsonTest.mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
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

class SerializeLeagueobjectModelTest {
    private static final String filepath = "src/test/java/dhl/mocks/";
    private static final String extension = ".json";
    private final String playerFileName = "--RetiredPlayersInLeague.json";

    SerializeDeserializeAbstractFactory factory;
    ISerializeLeagueObjectModel serializeLeagueobjectModel;
    IDeserializeLeagueObjectModel deserializeLeagueobjectModel;
    SerializeLeagueObjectModel objSerializeLeagueObjectModel;
    LeagueObjectModelMocks mockLeagueObjectModel;
    SerializedJsonMock mockSerializedJson;

    @BeforeEach
    public void initObject() {
        factory = SerializeDeserializeAbstractFactory.instance();
        serializeLeagueobjectModel = factory.createSerializeLeagueObjectModel(filepath);
        deserializeLeagueobjectModel = factory.createDeserializeLeagueObjectModel(filepath);
        objSerializeLeagueObjectModel = new SerializeLeagueObjectModel(filepath);
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        mockSerializedJson = new SerializedJsonMock();
    }

    @Test
    void serializeLeagueObjectModelTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();

        String serializedleagueModel = objSerializeLeagueObjectModel.serializeData(mockLeagueObjectModel.leagueModelMockWith30Players());

        String jsonFilePath = filepath + "Dhl" + extension;
        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMockWith30Players());

        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("Dhl");

        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
        Assertions.assertEquals(2, leagueObjectModel.getConferences().size());
        Assertions.assertEquals(2, leagueObjectModel.getFreeAgents().size());
        Assertions.assertEquals(2, leagueObjectModel.getCoaches().size());
        Assertions.assertEquals(2, leagueObjectModel.getGeneralManagers().size());

        deleteFile(jsonFilePath);
    }

    @Test
    void WriteSerializedLeagueObjectToFileTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        String jsonFilePath = filepath + "Dhl" + extension;

        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMockWith30Players());

        FileReader leagueObjectJsonReader = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJsonReader);
        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("Dhl");
        Assertions.assertNotNull(leagueObjectModel);
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());

        leagueObjectJsonReader.close();
        deleteFile(jsonFilePath);
    }

    @Test
    void UpdateSerializedLeagueObjectToJsonFileTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        String jsonFilePath = filepath + "Dhl" + extension;

        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMockWith30Players());
        serializeLeagueobjectModel.updateSerializedLeagueObjectToJsonFile(mockLeagueObjectModel.leagueModelMockWith30Players());

        FileReader leagueObjectJsonReader = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject objLeagueObject = (JSONObject) jsonParser.parse(leagueObjectJsonReader);

        leagueObjectModel = deserializeLeagueobjectModel.deserializeLeagueObjectJson("Dhl");

        Assertions.assertNotNull(leagueObjectModel);
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());

        leagueObjectJsonReader.close();
        deleteFile(jsonFilePath);
    }

    @Test
    void updateSerializedPlayerListToJsonFile() throws Exception {
        String leagueName = "DhlTest";
        List<IPlayer> playersList = new ArrayList<>();
        String jsonFilePath = filepath + leagueName + playerFileName;

        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics( 5, 5, 5, 5);
        playerStatistics1.setAge(50);
        players.add(new Player("Henry", "forward", false, playerStatistics1));

        serializeLeagueobjectModel.updateSerializedPlayerListToJsonFile(players, leagueName);

        FileReader playersJsonReader = new FileReader(jsonFilePath);
        JSONParser jsonParser = new JSONParser();
        JSONArray objPlayersObject = (JSONArray) jsonParser.parse(playersJsonReader);
        playersList = deserializeLeagueobjectModel.deserializePlayers(leagueName);
        Assertions.assertNotNull(playersList);
        Assertions.assertEquals(1, playersList.size());

        playersJsonReader.close();
        deleteFile(jsonFilePath);
    }

    @Test
    void writeJsonToFileTest() throws IOException {
        String jsonFilePath = filepath + "TestPlayerList" + extension;

        objSerializeLeagueObjectModel.writeJsonToFile(jsonFilePath, mockSerializedJson.serializedPlayerList());
        FileReader playersJson = new FileReader(jsonFilePath);
        Assertions.assertNotNull(playersJson);

        playersJson.close();
        deleteFile(jsonFilePath);
    }

    @Test
    void updateJsonFileTest() throws IOException, ParseException {
        String jsonFilePath = filepath + "Dhl" + playerFileName;

        objSerializeLeagueObjectModel.writeJsonToFile(jsonFilePath, mockSerializedJson.serializedPlayerList());
        objSerializeLeagueObjectModel.updateJsonFile(mockSerializedJson.serializedPlayerList(), jsonFilePath);

        FileReader playersJson = new FileReader(jsonFilePath);
        Assertions.assertNotNull(playersJson);

        playersJson.close();
        deleteFile(jsonFilePath);
    }

    void deleteFile(String path) throws IOException {
        File objFile = new File(path);

        if (objFile.exists()) {
            objFile.delete();
        }
    }
}
