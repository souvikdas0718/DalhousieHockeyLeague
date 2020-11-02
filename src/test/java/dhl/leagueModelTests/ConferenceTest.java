package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.factory.InitializeObjectFactory;
import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.Conference;
import dhl.leagueModel.Division;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ConferenceTest {
    InitializeObjectFactory initObj;
    IConference conference;
    IConference conferenceParameterized;
    IValidation validate;

    @BeforeEach()
    public void initObject() {
        initObj = new InitializeObjectFactory();
        conference = initObj.createConference();
        validate = new CommonValidation();
        LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();
        conferenceParameterized = leagueMock.getConferenceTestMock();
    }

    @Test
    public void ConferenceDefaultConstructorTest() {
        String conferenceName = conference.getConferenceName();
        Assertions.assertTrue(conferenceName.length() == 0);
        Assertions.assertTrue(conference.getDivisions().size() == 0);
    }

    @Test
    public void ConferenceTest() {
        Assertions.assertEquals("Western", conferenceParameterized.getConferenceName());
        Assertions.assertTrue(conferenceParameterized.getDivisions().size() > 0);
    }

    @Test
    public void getConferenceNameTest() {
        Assertions.assertEquals("Western", conferenceParameterized.getConferenceName());
    }

    @Test
    public void getDivisionsTest() {
        Assertions.assertTrue(conference.getDivisions().size() == 0);
    }

    @Test
    public void checkIfConferenceValidTest() throws Exception {
        List<IDivision> divisions = conferenceParameterized.getDivisions();
        divisions.add(new Division("Pacific", new ArrayList<ITeam>()));
        conferenceParameterized = new Conference("Western", divisions);
        Assertions.assertTrue(conferenceParameterized.checkIfConferenceValid(validate));
    }

    @Test
    void checkIfConferenceHasEvenDivisionsTest() {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            conferenceParameterized.checkIfConferenceValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("A conference must contain even number of divisions"));
    }

    @Test
    public void checkIfDivisionNamesUniqueInConferenceTest() throws Exception {
        List<IDivision> divisions = conferenceParameterized.getDivisions();
        divisions.add(new Division("Atlantic", new ArrayList<>()));
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            conferenceParameterized.checkIfConferenceValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of divisions inside a conference must be unique"));
    }

    @AfterEach()
    public void destroyObject() {
        initObj = null;
        conference = null;
    }

}
