package dhl.importJsonTest;

import dhl.inputOutput.importJson.SerializeDeserialize.DeserializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DeSerializeLeagueObjectModelTest {

    DeserializeLeagueObjectModel deserializeleagueObjectModel;
    SerializedJsonMock jsonMock;
    ILeagueObjectModel leagueObjectModel;
    JSONParser jsonParser;
    LeagueObjectModelMocks leagueObjectModelMocks;

    @BeforeEach
    public void initObject() {
        deserializeleagueObjectModel = new DeserializeLeagueObjectModel();
        jsonMock = new SerializedJsonMock();
        leagueObjectModel = new LeagueObjectModel();
        jsonParser = new JSONParser();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void deserializeLeagueObjectJsonTest() throws Exception {
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson(jsonLeagueObject);
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    public void updateLeagueObjectModelJsonTest() throws Exception {
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        JSONObject updatedJsonLeagueObject = deserializeleagueObjectModel.updateLeagueObjectModelJson(jsonLeagueObject);
        Assertions.assertEquals("Dhl", updatedJsonLeagueObject.get("leagueName"));
    }

    @Test
    public void deserializePlayersTest() throws ParseException {
        JSONArray jsonPlayerObject = (JSONArray) jsonParser.parse(jsonMock.serializedPlayerList());
        List<IPlayer> playersObject = deserializeleagueObjectModel.deserializePlayers(jsonPlayerObject);
        Assertions.assertEquals(1, playersObject.size());
    }
}
