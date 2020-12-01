package dhl.businessLogicTest.leagueModelTests;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertFalse(leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized));
    }

    @Test
    public void checkIfConferenceNamesUniqueInLeagueTest() {
        leagueModelParameterized = leagueMock.getLeagueObjectModelDuplicateConference();
        Assertions.assertFalse(leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized));
    }

    @Test
    void checkUserInputIncorrectLeagueTest()  {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Nhl", "Western", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
        Assertions.assertFalse(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @Test
    void checkUserInputIncorrectConferenceTest()  {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Premier", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
        Assertions.assertFalse(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @Test
    void checkUserInputIncorrectDivisionTest()  {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");
        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Metropolitan", newlyCreatedTeam, serializeLeagueObjectModel);
        Assertions.assertFalse(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @Test
    void checkUserInputTeamAlreadyPresentTest()  {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeam();
        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);

        Assertions.assertFalse(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @Test
    void checkUserInputForLeagueTest() {
        TeamMock teamMock =leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");

        ILeagueObjectModelInput leagueObjectModelInput = leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic",newlyCreatedTeam, serializeLeagueObjectModel);
        Assertions.assertTrue(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @Test
    void checkIfLeagueDetailsValidTest() {
        LeagueMock leagueMock =leagueMockFactory.createLeagueMock();
        List<IConference> conferences = leagueMock.getConferences();

        Assertions.assertTrue(leagueValidation.checkIfLeagueDetailsValid(conferences));
    }

    @AfterEach
    public void destroyObject() {
        validate = null;
        leagueValidation = null;
        leagueModelParameterized = null;
    }
}
