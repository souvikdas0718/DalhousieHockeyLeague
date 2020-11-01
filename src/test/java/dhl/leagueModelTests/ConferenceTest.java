package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.*;
import dhl.factory.InitializeObjectFactory;
import dhl.leagueModel.interfaceModel.*;
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
    public void initObject(){
        initObj = new InitializeObjectFactory();
        conference= initObj.createConference();
        validate=new CommonValidation();
        LeagueObjectModelMocks leagueMock= new LeagueObjectModelMocks();
        conferenceParameterized=leagueMock.getConferenceTestMock();
    }

    @Test
    public void ConferenceDefaultConstructorTest(){
        Assertions.assertTrue(conference.getConferenceName().isEmpty());
        Assertions.assertTrue(conference.getDivisions().isEmpty());
    }

    @Test
    public void ConferenceTest(){
        Assertions.assertEquals("Western",conferenceParameterized.getConferenceName());
        Assertions.assertTrue(conferenceParameterized.getDivisions().size()>0);
    }

    @Test
    public void getConferenceNameTest(){
        Assertions.assertEquals("Western",conferenceParameterized.getConferenceName());
    }

    @Test
    public void getDivisionsTest(){
        Assertions.assertTrue(conference.getDivisions().size()==0);
    }

    @Test
    public void checkIfConferenceValidTest() throws Exception{
        List<IDivision> divisions =conferenceParameterized.getDivisions();
        divisions.add(new Division("Pacific",new ArrayList<ITeam>()));
        conferenceParameterized=new Conference("Western",divisions);
        Assertions.assertTrue(conferenceParameterized.checkIfConferenceValid(validate));
    }

    @Test void checkIfConferenceHasEvenDivisionsTest(){
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            conferenceParameterized.checkIfConferenceValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("A conference must contain even number of divisions"));
    }

    @Test
    public void checkIfDivisionNamesUniqueInConferenceTest() throws Exception{
        List<IDivision> divisions =conferenceParameterized.getDivisions();
        divisions.add(new Division("Atlantic",new ArrayList<>()));
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            conferenceParameterized.checkIfConferenceValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of divisions inside a conference must be unique"));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        conference= null;
    }

}
