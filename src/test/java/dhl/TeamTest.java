package dhl;

import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IParserOutput;
import dhl.leagueModel.interfaceModel.ITeam;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamTest {
    InitializeObjectFactory initObj;
    ITeam team;
    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        team= initObj.createTeam();
    }

    @Test
    public void TeamDefaultConstructorTest(){
        Assert.assertTrue(team.getDivisionName().isEmpty());
        Assert.assertTrue(team.getConferenceName().isEmpty());
        Assert.assertTrue(team.getTeamName().isEmpty());
        Assert.assertTrue(team.getHeadCoach().isEmpty() );
        Assert.assertTrue(team.getGeneralManager().isEmpty());
    }
    @Test
    public void getTeamNameTest(){
        team.setTeamName("Edmonton Oilers");
        Assert.assertEquals("Edmonton Oilers",team.getTeamName());
    }
    @Test
    public void setTeamNameTest(){
        team.setTeamName("Boston Bruins");
        Assert.assertEquals("Boston Bruins",team.getTeamName());
    }

    @Test
    public void getGeneralManagerTest(){
        team.setGeneralManager("Bob Murray");
        Assert.assertEquals("Bob Murray",team.getGeneralManager());
    }
    @Test
    public void setGeneralManagerTest(){
        team.setGeneralManager("Don Sweeney");
        Assert.assertEquals("Don Sweeney",team.getGeneralManager());
    }

    @Test
    public void getHeadCoachTest(){
        team.setHeadCoach("Barry Trotz");
        Assert.assertEquals("Barry Trotz",team.getHeadCoach());

    }
    @Test
    public void setHeadCoachTest(){
        team.setHeadCoach("Todd McLellan");
        Assert.assertEquals("Todd McLellan",team.getHeadCoach());
    }

    @Test
    public void getDivisionNameTest(){
        team.setDivisionName("Atlantic");
        Assert.assertEquals("Atlantic",team.getDivisionName());

    }
    @Test
    public void setDivisionNameTest(){
        team.setDivisionName("Pacific");
        Assert.assertEquals("Pacific",team.getDivisionName());
    }

    @Test
    public void getConferenceNameTest(){
        team.setConferenceName("Western");
        Assert.assertEquals("Western",team.getConferenceName());

    }
    @Test
    public void setConferenceNameTest(){
        team.setConferenceName("Eastern");
        Assert.assertEquals("Eastern",team.getConferenceName());
    }
    @Test
    public void checkIfOneCaptainPerTeamTest(){
        team.setTeamName("Boston");
        IParserOutput iParserOutput=new MockParserOutput();
        Assert.assertTrue(team.checkIfOneCaptainPerTeam(iParserOutput));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        team= null;
    }

}
