package dhl.leagueModelTests;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Assertions.assertTrue(team.getDivisionName().isEmpty());
        Assertions.assertTrue(team.getConferenceName().isEmpty());
        Assertions.assertTrue(team.getTeamName().isEmpty());
        Assertions.assertTrue(team.getHeadCoach().isEmpty() );
        Assertions.assertTrue(team.getGeneralManager().isEmpty());
    }
    @Test
    public void TeamTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        Assertions.assertEquals("Ontario",team.getTeamName());
    }
    @Test
    public void getTeamNameTest(){
        team.setTeamName("Edmonton Oilers");
        Assertions.assertEquals("Edmonton Oilers",team.getTeamName());
    }
    @Test
    public void setTeamNameTest(){
        team.setTeamName("Boston Bruins");
        Assertions.assertEquals("Boston Bruins",team.getTeamName());
    }

    @Test
    public void getGeneralManagerTest(){
        team.setGeneralManager("Bob Murray");
        Assertions.assertEquals("Bob Murray",team.getGeneralManager());
    }
    @Test
    public void setGeneralManagerTest(){
        team.setGeneralManager("Don Sweeney");
        Assertions.assertEquals("Don Sweeney",team.getGeneralManager());
    }

    @Test
    public void getHeadCoachTest(){
        team.setHeadCoach("Barry Trotz");
        Assertions.assertEquals("Barry Trotz",team.getHeadCoach());

    }
    @Test
    public void setHeadCoachTest(){
        team.setHeadCoach("Todd McLellan");
        Assertions.assertEquals("Todd McLellan",team.getHeadCoach());
    }

    @Test
    public void getDivisionNameTest(){
        team.setDivisionName("Atlantic");
        Assertions.assertEquals("Atlantic",team.getDivisionName());

    }
    @Test
    public void setDivisionNameTest(){
        team.setDivisionName("Pacific");
        Assertions.assertEquals("Pacific",team.getDivisionName());
    }

    @Test
    public void getConferenceNameTest(){
        team.setConferenceName("Western");
        Assertions.assertEquals("Western",team.getConferenceName());

    }
    @Test
    public void setConferenceNameTest(){
        team.setConferenceName("Eastern");
        Assertions.assertEquals("Eastern",team.getConferenceName());
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
        Assertions.assertTrue(errorMsg.getMessage().contains("Please select captain for the team"));
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
        Assertions.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }
    @Test
    public void checkNumberOfPlayersInTeamTest(){
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        IParserOutput iParserOutput=new MockParserOutput();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        iParserOutput.setPlayers(playersList);
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(playersList));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception{
        team = new Team("Ontario","Mathew","henry","Atlantic","Western");
        IParserOutput iParserOutput=new MockParserOutput();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,"Ontario"));
        playersList.add(new Player("Max","goalie",true,"Ontario"));
        iParserOutput.setPlayers(playersList);
        Assertions.assertTrue(team.checkIfTeamValid(iParserOutput,validate));
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
        Assertions.assertTrue(error.getMessage().contains("Number of players cannot exceed 20 in each team"));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        team= null;
    }

}
