package dhl.importJsonTest;

import dhl.InputOutput.importJson.DeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.InputOutput.importJson.SerializeLeagueobjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SerializeLeagueobjectModelTest {

    ISerializeLeagueobjectModel serializeLeagueobjectModel;
    IDeserializeLeagueObjectModel deserializeLeagueobjectModel;
    LeagueObjectModelMocks mockLeagueObjectModel;
    SerializedJsonMock mockSerializedJson;

    @BeforeEach
    public void initObject() {
        serializeLeagueobjectModel = new SerializeLeagueobjectModel();
        deserializeLeagueobjectModel = new DeserializeLeagueObjectModel();
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        mockSerializedJson = new SerializedJsonMock();
    }

    @Test
    void serializeLeagueObjectModelTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();

        String serializedleagueModel = serializeLeagueobjectModel.serializeLeagueObjectModel(mockLeagueObjectModel.leagueModelMock());

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
}
