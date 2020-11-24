package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.ConferenceMock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


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
    void checkIfConferenceHasEvenDivisionsTest() {
        conferenceParameterized = conferenceMock.getConferenceWithTwoDivisions();
        Assertions.assertTrue( conferenceParameterized.checkIfConferenceHasEvenDivisions());
    }

    @Test
    void checkIfConferenceHasOddDivisionsTest() {
        conferenceParameterized = conferenceMock.getConferenceOddDivision();
        conferenceParameterized.checkIfConferenceHasEvenDivisions();
        Assertions.assertFalse(conferenceParameterized.checkIfConferenceHasEvenDivisions() );
    }

    @Test
    public void checkIfDivisionNamesUniqueInConferenceTest() {
        conferenceParameterized = conferenceMock.getConferenceWithSameDivisions();
        Assertions.assertFalse( conferenceParameterized.checkIfConferenceHasUniqueDivisions());
    }

    @Test
    public void checkIfDivisionNamesValidConferenceTest() {
        conferenceParameterized = conferenceMock.getConference();
        Assertions.assertTrue( conferenceParameterized.checkIfConferenceHasUniqueDivisions());
    }

    @AfterEach()
    public void destroyObject() {
        conference = null;
    }

}
