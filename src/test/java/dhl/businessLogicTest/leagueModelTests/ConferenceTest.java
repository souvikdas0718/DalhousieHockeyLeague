package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.ConferenceMock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ConferenceTest {
    IConference conference;
    IConference conferenceParameterized;
    IValidation validate;
    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;
    ConferenceMock conferenceMock;

    @BeforeEach()
    public void initObject() {
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        conference = factory.createConferenceDefault();
        validate = factory.createCommonValidation();
        conferenceMock = mockFactory.createConferenceMock();
        conferenceParameterized = conferenceMock.getConference();
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
        conferenceParameterized = conferenceMock.getConferenceWithTwoDivisions();
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
    public void checkIfDivisionNamesUniqueInConferenceTest() {
        conferenceParameterized = conferenceMock.getConferenceWithSameDivisions();
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            conferenceParameterized.checkIfConferenceValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of divisions inside a conference must be unique"));
    }

    @AfterEach()
    public void destroyObject() {
        conference = null;
    }

}
