package dhl.businessLogicTest.leagueModelTests;

import dhl.mocks.MockSerializeLeagueObjectModel;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    IValidation validate;
    ILeagueObjectModelValidation leagueValidation;
    ILeagueObjectModel leagueModelParameterized;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    MockAbstractFactory mockFactory;

    @BeforeEach
    public void initialize()  {
        leagueFactory= LeagueModelAbstractFactory.instance();
        ILeagueObjectModelBuilder leagueBuilder = new LeagueObjectModelBuilder();
        leagueModel = (LeagueObjectModel) leagueBuilder.getResult();

        validate = leagueFactory.createCommonValidation();
        leagueValidation = leagueFactory.createLeagueObjectModelValidation();

        leagueMockFactory=LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();
        mockFactory= MockAbstractFactory.instance();
        leagueModelParameterized = leagueMock.getLeagueObjectModel();
    }

    @Test
    public void LeagueObjectModelTest() {
        String leagueName = leagueModel.getLeagueName();
        Assertions.assertTrue(leagueName.length() == 0);
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
        Assertions.assertTrue(leagueModel.conferences.size() == 0);
        List<IGeneralManager> managers = leagueModelParameterized.getGeneralManagers();
        Assertions.assertTrue( managers.size()>0);
        Assertions.assertNotNull(leagueModelParameterized.getGameConfig());
    }

    @Test
    public void setFreeAgentsTest() {
        List<IPlayer> freeAgentsList = leagueMock.getFreeAgents();
        leagueModel.setFreeAgents(leagueMock.getFreeAgents());
        Assertions.assertEquals(freeAgentsList.size(),leagueModel.getFreeAgents().size());
    }

    @Test
    public void getCoachesTest() {
        List<ICoach> coaches = leagueMock.getCoaches();
        leagueModel.setCoaches(coaches);
        Assertions.assertEquals(coaches.size(), leagueModel.getCoaches().size());
    }

    @Test
    public void setManagersTest() {
        List<IGeneralManager> generalManagers = leagueMock.getManagers();
        leagueModel.setManagers(generalManagers);
        Assertions.assertEquals(generalManagers.size(), leagueMock.getManagers().size());
    }

    @Test
    public void setLeagueNameTest() {
        leagueModelParameterized.setLeagueName("National Hockey League");
        Assertions.assertEquals("National Hockey League", leagueModelParameterized.getLeagueName());
    }

    @Test
    public void checkIfLeagueModelValidTest() {
        leagueModelParameterized.checkIfLeagueModelValid(validate,leagueValidation);
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
    }

    @Test
    public void saveLeagueObjectModelTest() throws IOException {
        ISerializeLeagueObjectModel mockSerialize = mockFactory.getMockSerialize();
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

        ILeagueObjectModelInput leagueInput =leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, mockSerialize);
        leagueModelParameterized = leagueModelParameterized.saveLeagueObjectModel(mockSerialize, leagueInput);
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
    }

    @Test
    public void loadLeagueObjectModelTest() throws IOException, ParseException {
        IDeserializeLeagueObjectModel mockDeserialize = mockFactory.getMockDeserialize();
        Assertions.assertEquals("Dhl", leagueModelParameterized.loadLeagueObjectModel(mockDeserialize, "Dhl", "Nova Scotia").getLeagueName());
    }

    @Test
    public void updateLeagueObjectModel() throws IOException {
        ISerializeLeagueObjectModel serializeMock = new MockSerializeLeagueObjectModel();
        Assertions.assertEquals("Dhl", leagueModelParameterized.updateLeagueObjectModel(serializeMock).getLeagueName());
    }

    @AfterEach
    public void destroyObject() {
        leagueModel = null;
    }

}


