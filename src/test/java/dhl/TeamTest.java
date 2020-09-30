package dhl;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TeamTest {
    InitializeObjectFactory initObj;
    IValidation validate;
    ITeam team;
    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        team= initObj.createTeam();
        validate=new CommonValidation();
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
    public void TeamTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        Assert.assertEquals("Ontario",team.getTeamName());
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
    public void checkIfOneCaptainPerTeamErrorTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        playersList.add(new Player("Max","goalie",false,"Ontario"));
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assert.assertTrue(errorMsg.getMessage().contains("Please select captain for the team"));
    }
    @Test
    public void checkIfOneCaptainPerTeamTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",true,"Ontario"));
        playersList.add(new Player("Max","goalie",true,"Ontario"));
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assert.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }
    @Test
    public void checkNumberOfPlayersInTeamTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        IParserOutput iParserOutput=new MockParserOutput();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        iParserOutput.setPlayers(playersList);
        Assert.assertTrue(team.checkIfSizeOfTeamValid(playersList));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception{
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        IParserOutput iParserOutput=new MockParserOutput();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        playersList.add(new Player("Max","goalie",true,"Ontario"));
        iParserOutput.setPlayers(playersList);
        Assert.assertTrue(team.checkIfTeamValid(iParserOutput,validate));
    }
    @Test
    public void checkIfTeamPlayerMoreThan20Test() throws Exception{
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        IParserOutput iParserOutput=new MockParserOutput();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Max","goalie",true,"Ontario"));
        for(int i=0;i<20;i++){
            playersList.add(new Player("Henry","forward",false,"Ontario"));
        }
        iParserOutput.setPlayers(playersList);
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfTeamValid(iParserOutput,validate);
        });
        Assert.assertTrue(error.getMessage().contains("Number of players cannot exceed 20 in each team"));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        team= null;
    }

}
