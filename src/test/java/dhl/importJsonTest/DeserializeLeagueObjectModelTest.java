package dhl.importJsonTest;

import dhl.mocks.LeagueObjectModelMocks;
import dhl.importJsonTest.mocks.SerializedJsonMock;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DeserializeLeagueObjectModelTest {
    private static final String filepath = "src/test/java/dhl/importJsonTest/mocks/";
    final String playerFileName = "--RetiredPlayersInLeague.json";
    final String jsonExtension = ".json";

    ImportJsonTestAbstractFactory importJsonTestAbstractFactory;
    SerializeDeserializeAbstractFactory factory;
    IDeserializeLeagueObjectModel deserializeleagueObjectModel;
    SerializedJsonMock jsonMock;
    ILeagueObjectModel leagueObjectModel;
    JSONParser jsonParser;
    LeagueObjectModelMocks leagueObjectModelMocks;
    ISerializeLeagueObjectModel serializeLeagueobjectModel;

    @BeforeEach
    public void initObject() {
        importJsonTestAbstractFactory = ImportJsonTestAbstractFactory.instance();
        factory = SerializeDeserializeAbstractFactory.instance();
        serializeLeagueobjectModel = factory.createSerializeLeagueObjectModel(filepath);
        deserializeleagueObjectModel = factory.createDeserializeLeagueObjectModel(filepath);
        jsonMock = importJsonTestAbstractFactory.createSerializedJsonMock();
        leagueObjectModel = new LeagueObjectModel();
        jsonParser = new JSONParser();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void deserializeLeagueObjectJsonTest() throws Exception {
        leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson("DhlMockLeagueObjectModel");
        Assertions.assertEquals("DhlMockLeagueObjectModel", leagueObjectModel.getLeagueName());

    }

    @Test
    public void updateLeagueObjectModelJsonTest() throws Exception {
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        DeserializeLeagueObjectModel objdeserializeLeagueObjectModel = new DeserializeLeagueObjectModel(filepath);
        JSONObject updatedJsonLeagueObject = objdeserializeLeagueObjectModel.updateLeagueObjectModelJson(jsonLeagueObject);
        Assertions.assertEquals("DhlMockLeagueObjectModel", updatedJsonLeagueObject.get("leagueName"));
    }

    @Test
    public void deserializePlayersTest() throws Exception {
        List<IPlayer> playersObject = deserializeleagueObjectModel.deserializePlayers("DhlMockRetiredPlayers");
        Assertions.assertEquals(1, playersObject.size());

    }
}
