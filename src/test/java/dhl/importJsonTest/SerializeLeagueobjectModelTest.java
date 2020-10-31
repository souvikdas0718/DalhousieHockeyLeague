package dhl.importJsonTest;

import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.leagueModel.*;
import dhl.InputOutput.importJson.SerializeLeagueobjectModel;
import dhl.leagueModel.interfaceModel.*;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import dhl.InputOutput.importJson.DeserializeLeagueObjectModel;

public class SerializeLeagueobjectModelTest {
    ISerializeLeagueobjectModel objSerialize;
    IDeserializeLeagueObjectModel objDeserialize;
    LeagueObjectModelMocks mocks;

    @BeforeEach
    public void initObject(){
        mocks = new LeagueObjectModelMocks();
        objSerialize = new SerializeLeagueobjectModel();
        objDeserialize = new DeserializeLeagueObjectModel();
    }
    @Test
    void serializeLeagueObjectModelTest() throws Exception{
        ILeagueObjectModel leagueObjectModel=new LeagueObjectModel();
        dhl.Mocks.SerializedJsonMock jsonMock = new dhl.Mocks.SerializedJsonMock();
        String str = objSerialize.serializeLeagueObjectModel(mocks.leagueModelMock());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(str);

        leagueObjectModel = objDeserialize.deserializeLeagueObjectJson(jsonLeagueObject);

        Assertions.assertEquals(str, jsonMock.serializedJson());
        Assertions.assertEquals("Dhl",leagueObjectModel.getLeagueName());
        Assertions.assertEquals(1,leagueObjectModel.getConferences().size());
        Assertions.assertEquals(2,leagueObjectModel.getFreeAgents().size());
        Assertions.assertEquals(2,leagueObjectModel.getCoaches().size());
        Assertions.assertEquals(2,leagueObjectModel.getGeneralManagers().size());
    }



}
