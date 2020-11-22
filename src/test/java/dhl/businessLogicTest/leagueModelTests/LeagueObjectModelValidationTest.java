package dhl.businessLogicTest.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.Mocks.MockSerializeLeagueObjectModel;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModelValidationTest {
    IValidation validate;
    ILeagueObjectModelValidation leagueValidation;
    ILeagueObjectModel leagueModelParameterized;
    LeagueMock leagueMock;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    MockAbstractFactory mockFactory;

    @BeforeEach
    public void initialize() {
        leagueFactory= LeagueModelAbstractFactory.instance();
        validate = leagueFactory.createCommonValidation();
        leagueValidation = leagueFactory.createLeagueObjectModelValidation();

        leagueMockFactory=LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();
        leagueModelParameterized = leagueMock.getLeagueObjectModel();

        mockFactory =MockAbstractFactory.instance();
        serializeLeagueObjectModel = mockFactory.getMockSerialize();
    }

    @Test
    public void checkIfLeagueObjectModelValidTest()  {
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
    }

    @Test
    void checkIfLeagueHasEvenConferencesTest() {

        leagueModelParameterized= leagueMock.getLeagueObjectModelOddConf();
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("A League must contain even number of conferences"));
    }

    @Test
    public void checkIfConferenceNamesUniqueInLeagueTest() {
        leagueModelParameterized = leagueMock.getLeagueObjectModelDuplicateConference();
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of conferences inside a league must be unique"));
    }

    @Test
    void checkUserInputIncorrectLeagueTest()  {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            TeamMock teamMock =leagueMockFactory.createTeamMock();
            ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

            ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Nhl", "Western", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("League name is not present in file imported."));
    }

    @Test
    void checkUserInputIncorrectConferenceTest()  {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            TeamMock teamMock =leagueMockFactory.createTeamMock();
            ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

            ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Premier", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Conference name is not present in file imported"));
    }

    @Test
    void checkUserInputIncorrectDivisionTest()  {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            TeamMock teamMock =leagueMockFactory.createTeamMock();
            ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");
            ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Metropolitan", newlyCreatedTeam, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Division name is not present in file imported"));
    }

    @Test
    void checkUserInputTeamAlreadyPresentTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            TeamMock teamMock =leagueMockFactory.createTeamMock();
            ITeam newlyCreatedTeam = teamMock.getTeam();
            ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Team name entered is already present in file imported"));
    }

    @Test
    void checkUserInputForLeagueTest() throws Exception {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic",newlyCreatedTeam, serializeLeagueObjectModel);
        Assertions.assertTrue(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @AfterEach
    public void destroyObject() {
        validate = null;
        leagueValidation = null;
        leagueModelParameterized = null;
    }
}
