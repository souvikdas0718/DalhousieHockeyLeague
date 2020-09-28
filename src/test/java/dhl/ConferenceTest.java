package dhl;

import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.interfaceModel.IConference;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConferenceTest {
    InitializeObjectFactory initObj;
    IConference conference;
    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        conference= initObj.createConference();
    }

    @Test
    public void ConferenceDefaultConstructorTest(){
        Assert.assertTrue(conference.getConferenceName().isEmpty());
    }

    @Test
    public void getConferenceNameTest(){
        conference.setConferenceName("Eastern");
        Assert.assertEquals("Eastern",conference.getConferenceName());
    }
    @Test
    public void setConferenceNameTest(){
        conference.setConferenceName("Western");
        Assert.assertEquals("Western",conference.getConferenceName());
    }
    @Test
    public void getLeagueNameTest(){
        conference.setLeagueName("Dalhousie Hockey League");
        Assert.assertEquals("Dalhousie Hockey League",conference.getLeagueName());
    }
    @Test
    public void setLeagueNameTest(){
        conference.setLeagueName("Dalhousie Hockey League");
        Assert.assertEquals("Dalhousie Hockey League",conference.getLeagueName());
    }
    @AfterEach()
    public void destroyObject(){
        initObj = null;
        conference= null;
    }
}
