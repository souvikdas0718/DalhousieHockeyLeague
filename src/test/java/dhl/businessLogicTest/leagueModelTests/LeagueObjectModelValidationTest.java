package dhl.businessLogicTest.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
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
    LeagueObjectModelMocks leagueMock;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;

    @BeforeEach
    public void initialize() {
        validate = new CommonValidation();
        leagueValidation = new LeagueObjectModelValidation();
        leagueMock = new LeagueObjectModelMocks();
        leagueModelParameterized = leagueMock.getLeagueObjectMock();
        serializeLeagueObjectModel = new MockSerializeLeagueObjectModel();
    }

    @Test
    public void checkIfLeagueObjectModelValidTest() throws Exception {
        List<IConference> conferences = leagueModelParameterized.getConferences();
        conferences.add(new Conference("Eastern", new ArrayList<>()));
        leagueModelParameterized = new LeagueObjectModel("Dhl", conferences, leagueMock.getFreeAgentArrayMock(), new ArrayList<>(), new ArrayList<>(), new GameConfig(new JSONObject()));
        Assertions.assertEquals("Dhl", leagueModelParameterized.getLeagueName());
    }

    @Test
    void checkIfLeagueHasEvenConferencesTest() {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("A League must contain even number of conferences"));
    }

    @Test
    public void checkIfConferenceNamesUniqueInLeagueTest() {
        List<IConference> conferences = leagueModelParameterized.getConferences();
        conferences.add(new Conference("Western", new ArrayList<>()));
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            leagueValidation.checkIfLeagueObjectModelValid(validate, leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of conferences inside a league must be unique"));
    }

    @Test
    void checkUserInputIncorrectLeagueTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {

            ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput("Nhl", "Western", "Atlantic", leagueMock.getTeamObjectMock(), leagueValidation, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("League name is not present in file imported."));
    }

    @Test
    void checkUserInputIncorrectConferenceTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput("Dhl", "Premier", "Atlantic", leagueMock.getTeamObjectMock(), leagueValidation, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Conference name is not present in file imported"));
    }

    @Test
    void checkUserInputIncorrectDivisionTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput("Dhl", "Western", "Metropolitan", leagueMock.getTeamObjectMock(), leagueValidation, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Division name is not present in file imported"));
    }

    @Test
    void checkUserInputTeamAlreadyPresentTest() throws Exception {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            List<ICoach> coaches = leagueMock.getCoaches();
            ITeam newlyCreatedTeam = new Team("Ontario", "harry", coaches.get(0), leagueMock.getPlayerArrayMock());
            ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, leagueValidation, serializeLeagueObjectModel);
            leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput);
        });
        Assertions.assertTrue(error.getMessage().contains("Team name entered is already present in file imported"));
    }

    @Test
    void checkUserInputForLeagueTest() throws Exception {
        ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput("Dhl", "Western", "Atlantic", leagueMock.getTeamObjectMock(), leagueValidation, serializeLeagueObjectModel);
        Assertions.assertTrue(leagueValidation.checkUserInputForLeague(leagueModelParameterized, leagueObjectModelInput));
    }

    @AfterEach
    public void destroyObject() {
        validate = null;
        leagueValidation = null;
        leagueModelParameterized = null;
    }
}
