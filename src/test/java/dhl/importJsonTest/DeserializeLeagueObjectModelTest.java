package dhl.importJsonTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

import dhl.inputOutput.importJson.serializeDeserialize.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeLeagueObjectModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeserializeLeagueObjectModelTest {
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
        leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson("DhlMockLeagueObjectModel");
        Assertions.assertEquals("Dalhousie Hockey League", leagueObjectModel.getLeagueName());
    }

    @Test
    public void updateLeagueObjectModelJsonTest() throws Exception {
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        JSONObject updatedJsonLeagueObject = deserializeleagueObjectModel.updateLeagueObjectModelJson(jsonLeagueObject);
        Assertions.assertEquals("Dhl", updatedJsonLeagueObject.get("leagueName"));
    }

    @Test
    public void deserializePlayersTest() throws Exception {
        List<IPlayer> playersObject = deserializeleagueObjectModel.deserializePlayers("DhlMockRetiredPlayers");
        Assertions.assertEquals(1, playersObject.size());
    }
}
