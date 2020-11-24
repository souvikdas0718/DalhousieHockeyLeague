package dhl.importJsonTest;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.inputOutput.importJson.serializeDeserialize.DeserializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeLeagueObjectModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeSerializeLeagueObjectModelTest {
    private static final String filepath = "src/test/java/dhl/Mocks/";
    final String playerFileName = "--RetiredPlayersInLeague.json";
    final String jsonExtension = ".json";

    DeserializeLeagueObjectModel deserializeleagueObjectModel;
    SerializedJsonMock jsonMock;
    ILeagueObjectModel leagueObjectModel;
    JSONParser jsonParser;
    LeagueObjectModelMocks leagueObjectModelMocks;
    SerializeLeagueObjectModel serializeLeagueobjectModel;

    @BeforeEach
    public void initObject() {
        serializeLeagueobjectModel = new SerializeLeagueObjectModel(filepath);
        deserializeleagueObjectModel = new DeserializeLeagueObjectModel(filepath);
        jsonMock = new SerializedJsonMock();
        leagueObjectModel = new LeagueObjectModel();
        jsonParser = new JSONParser();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void deserializeLeagueObjectJsonTest() throws Exception {
        String leagueObjectModelJsonPath = filepath + "Dhl" + jsonExtension;
        serializeLeagueobjectModel.writeSerializedLeagueObjectToJsonFile(leagueObjectModelMocks.leagueModelMockWith30Players());
        leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson("Dhl");
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());

        deleteFile(leagueObjectModelJsonPath);
    }

    @Test
    public void updateLeagueObjectModelJsonTest() throws Exception {
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        JSONObject updatedJsonLeagueObject = deserializeleagueObjectModel.updateLeagueObjectModelJson(jsonLeagueObject);
        Assertions.assertEquals("Dhl", updatedJsonLeagueObject.get("leagueName"));
    }

    @Test
    public void deserializePlayersTest() throws Exception {
        JSONArray jsonPlayerObject = (JSONArray) jsonParser.parse(jsonMock.serializedPlayerList());
        String jsonFilePath = filepath + "Dhl" + playerFileName;
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics( 5, 5, 5, 5);
        playerStatistics1.setAge(50);
        players.add(new Player("Henry", "forward", false, playerStatistics1));

        serializeLeagueobjectModel.updateSerializedPlayerListToJsonFile(players, "Dhl");
        List<IPlayer> playersObject = deserializeleagueObjectModel.deserializePlayers("Dhl");
        Assertions.assertEquals(1, playersObject.size());

        deleteFile(jsonFilePath);
    }

    void deleteFile(String path) throws IOException {
        File objFile = new File(path);

        if(objFile.exists()) {
            objFile.delete();
        }
    }
}
