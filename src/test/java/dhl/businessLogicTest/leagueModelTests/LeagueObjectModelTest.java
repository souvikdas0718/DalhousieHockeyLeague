package dhl.businessLogicTest.leagueModelTests;

import dhl.inputOutput.importJson.GameConfig;
import dhl.inputOutput.importJson.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.ISerializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    IValidation validate;
    ILeagueObjectModelValidation leagueValidation;
    ILeagueObjectModel leagueModelParameterized;
    LeagueObjectModelMocks leagueMock;

    @BeforeEach
    public void initialize() {
        leagueModel = new LeagueObjectModel();
        validate = new CommonValidation();
        leagueValidation = new LeagueObjectModelValidation();
        leagueMock = new LeagueObjectModelMocks();
        leagueModelParameterized = leagueMock.getLeagueObjectMock();
    }

    @Test
    public void LeagueObjectModelTest() {
        String leagueName = leagueModel.getLeagueName();
        Assertions.assertTrue(leagueName.length() == 0);
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
        Assertions.assertTrue(leagueModel.conferences.size() == 0);
        List<IGeneralManager> managers = leagueModelParameterized.getGeneralManagers();
        Assertions.assertEquals(3, managers.size());
        Assertions.assertNotNull(leagueModelParameterized.getGameConfig());
    }

    @Test
    public void getFreeAgentsTest() {
        List<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayerStatistics playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
        freeAgentsList.add(new FreeAgent("Henry", "forward", playerStatistics));
        freeAgentsList.add(new FreeAgent("Max", "goalie", playerStatistics));
        leagueModel = new LeagueObjectModel("Dhl", leagueMock.getConferenceArrayMock(), freeAgentsList, new ArrayList<>(), new ArrayList<>(), new GameConfig(new JSONObject()));
        Assertions.assertEquals(leagueModel.getFreeAgents().size(), freeAgentsList.size());
    }

    @Test
    public void getCoachesTest() {
        leagueModel.setCoaches(leagueMock.getCoaches());
        Assertions.assertEquals(2, leagueModel.getCoaches().size());
    }

    @Test
    public void setManagersTest() {
        Assertions.assertEquals(3, leagueMock.getManagers().size());
    }

    @Test
    public void checkIfLeagueModelValidTest() throws Exception {
        List<IConference> conferences = leagueModelParameterized.getConferences();
        conferences.add(new Conference("Eastern", new ArrayList<>()));
        leagueModelParameterized = new LeagueObjectModel("Dhl", conferences, leagueMock.getFreeAgentArrayMock(), new ArrayList<>(), new ArrayList<>(), new GameConfig(new JSONObject()));
        Assertions.assertTrue(leagueModelParameterized.checkIfLeagueModelValid(validate, leagueValidation));
    }

    @Test
    public void saveLeagueObjectModelTest() throws Exception {
        ISerializeLeagueObjectModel mockSerializeLeagueObjectModel = new MockSerializeLeagueObjectModel();
        List<IPlayer> players = new ArrayList<>();
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ITeam newlyCreatedTeam = new Team("Nova Scotia", "Mathew", headCoach, players);

        ILeagueObjectModelInput leagueInput = new LeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, leagueValidation, mockSerializeLeagueObjectModel);
        leagueModelParameterized = leagueModelParameterized.saveLeagueObjectModel(mockSerializeLeagueObjectModel, leagueInput);
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
    }

    @Test
    public void loadLeagueObjectModelTest() throws Exception {
        IDeserializeLeagueObjectModel mockDeserializeLeagueObjectModel = new MockDeserializeLeagueObjectModel();
        // TODO: 18-11-2020 Rajni Update This  
        //Assertions.assertEquals("Dhl", leagueModelParameterized.loadLeagueObjectModel(mockDeserializeLeagueObjectModel, "Dhl", "Nova Scotia").getLeagueName());
    }

    @Test
    public void updateLeagueObjectModel() throws Exception {
        ISerializeLeagueObjectModel mock = new MockSerializeLeagueObjectModel();
        Assertions.assertEquals("Dhl", leagueModelParameterized.updateLeagueObjectModel(mock).getLeagueName());
    }

    @AfterEach
    public void destroyObject() {
        leagueModel = null;
    }

}


