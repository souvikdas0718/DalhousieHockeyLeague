package dhl.leagueModelTests;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        playersList.add(new Player("Max","goalie",true,"Ontario"));
        ITeam team = new Team("Ontario","Mathew","henry",playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        conferenceParameterized=new Conference("Western",divisionsList);
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
        conference.setConferenceName("Eastern");
        Assertions.assertEquals("Eastern",conference.getConferenceName());
    }
    @Test
    public void setConferenceNameTest(){
        conference.setConferenceName("Western");
        Assertions.assertEquals("Western",conference.getConferenceName());
    }

    @Test
    public void getDivisionsTest(){
        conference.setDivisions(new ArrayList<IDivision>());
        Assertions.assertTrue(conference.getDivisions().size()==0);
    }
    @Test
    public void setDivisionsTest(){
        conference.setDivisions(new ArrayList<IDivision>());
        Assertions.assertTrue(conference.getDivisions().size()==0);
    }
    @Test
    public void checkIfConferenceValidTest() throws Exception{

        Assertions.assertTrue(conferenceParameterized.checkIfConferenceValid(validate));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        conference= null;
    }
}