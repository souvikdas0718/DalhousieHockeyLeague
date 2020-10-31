package dhl.importJsonTest;

import dhl.InputOutput.importJson.DeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dhl.Mocks.SerializedJsonMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DeSerializeLeagueObjectModelTest {
    DeserializeLeagueObjectModel objDeserialize;
    SerializedJsonMock jsonMock;

    @BeforeEach
    public void initObject(){
        objDeserialize = new DeserializeLeagueObjectModel();
        jsonMock = new SerializedJsonMock();
    }

    @Test
    public void deserializeLeagueObjectJsonTest() throws Exception {
        ILeagueObjectModel leagueObjectModel=new LeagueObjectModel();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());
        leagueObjectModel = objDeserialize.deserializeLeagueObjectJson(jsonLeagueObject);

        Assertions.assertEquals("Dhl",leagueObjectModel.getLeagueName());
    }
    public void deserializeCoachTest() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());

        JSONArray coachesJsonArrayList = (JSONArray) jsonLeagueObject.get("coaches");
        ArrayList<ICoach> coaches = objDeserialize.deserializeCoach(coachesJsonArrayList);

        Assertions.assertEquals(2,coaches.size());
    }
    public void deserializeGeneralManagerTest() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());

        JSONArray coachesJsonArrayList = (JSONArray) jsonLeagueObject.get("managers");
        ArrayList<IGeneralManager> managers = objDeserialize.deserializeGeneralManager(coachesJsonArrayList);

        Assertions.assertEquals(2,managers.size());
    }
    public void deserializeFreeAgentTest() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());

        ArrayList<IFreeAgent> freeAgents = objDeserialize.deserializeFreeAgent(jsonLeagueObject);

        Assertions.assertEquals(2,freeAgents.size());
    }
    public void deserializeConferenceTest() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(jsonMock.serializedJson());

        ArrayList<IConference> conferences = objDeserialize.deserializeConference(jsonLeagueObject);

        Assertions.assertEquals(1,conferences.size());
    }
    public void deserializeTeamTest() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonDivisionObject = (JSONObject) jsonParser.parse(jsonMock.divJson());
        ArrayList<ITeam> teams = objDeserialize.deserializeTeam(jsonDivisionObject);
        Assertions.assertEquals(2,teams.size());
    }
}
