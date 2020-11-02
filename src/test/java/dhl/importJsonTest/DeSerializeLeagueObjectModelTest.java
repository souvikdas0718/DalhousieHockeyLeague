package dhl.importJsonTest;

import dhl.InputOutput.importJson.DeserializeLeagueObjectModel;
import dhl.Mocks.SerializedJsonMock;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeSerializeLeagueObjectModelTest {

    DeserializeLeagueObjectModel deserializeleagueObjectModel;
    SerializedJsonMock jsonMock;
    ILeagueObjectModel leagueObjectModel;
    JSONParser jsonParser;

    @BeforeEach
    public void initObject() {
        deserializeleagueObjectModel = new DeserializeLeagueObjectModel();
        jsonMock = new SerializedJsonMock();
        leagueObjectModel = new LeagueObjectModel();
        jsonParser = new JSONParser();
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
}
