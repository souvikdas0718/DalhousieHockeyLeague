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

public class TeamTest {
    InitializeObjectFactory initObj;
    IValidation validate;
    ITeam team;
    ArrayList<IPlayer> playerArrayList;
    IPlayerStatistics playerStatistics;
    ICoach headCoach;

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        team= initObj.createTeam();
        validate=new CommonValidation();
        playerArrayList=new ArrayList<>();
        playerStatistics =new PlayerStatistics(20,10,10,10,10);
        playerArrayList.add(new Player("Harry","forward",false,playerStatistics));
        headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
    }

    @Test
    public void TeamDefaultConstructorTest(){
        Assertions.assertTrue(team.getTeamName().isEmpty());
        Assertions.assertTrue(team.getHeadCoach().getCoachName().isEmpty() );
        Assertions.assertTrue(team.getGeneralManager().isEmpty());
        Assertions.assertEquals(0,team.getPlayers().size());
    }

    @Test
    public void TeamTest(){
        team = new Team("Ontario","Mathew",headCoach, playerArrayList);
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
        ICoach coach = new Coach("Barry Trotz",0.1,0.5,1.0,0);
        team.setHeadCoach(coach);
        Assertions.assertEquals("Barry Trotz",team.getHeadCoach().getCoachName());

    }

    @Test
    public void setHeadCoachTest(){
        ICoach coach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        team.setHeadCoach(coach);
        Assertions.assertEquals("Todd McLellan",team.getHeadCoach().getCoachName());
    }

    @Test
    public void getPlayersTest(){
        team.setPlayers(playerArrayList);
        Assertions.assertTrue(team.getPlayers().size()>0);

    }

    @Test
    public void setPlayersTest(){
        team.setPlayers(playerArrayList);
        Assertions.assertTrue(team.getPlayers().size()!=0);
    }

    @Test
    public void checkIfOneCaptainPerTeamErrorTest(){
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",false,playerStatistics));
        team = new Team("Ontario","Mathew",headCoach,playersList);
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Please select captain for the team"));
    }

    @Test
    public void checkIfOneCaptainPerTeamTest(){
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",true,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        team = new Team("Ontario","Mathew",headCoach,playersList);
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }

    @Test
    public void checkNumberOfPlayersInTeamTest(){
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",true,playerStatistics));
        for(int i=1;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false,playerStatistics));
        }
        team = new Team("Ontario","Mathew",headCoach,playersList);
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(playersList));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception{
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        for(int i=1;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false,playerStatistics));
        }
        team = new Team("Ontario","Mathew",headCoach,playersList);
        Assertions.assertTrue(team.checkIfTeamValid(validate));
    }

    @Test
    public void checkIfTeamPlayerMoreThan20Test() throws Exception{
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        for(int i=0;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false,playerStatistics));
        }
        team = new Team("Ontario","Mathew",headCoach,playersList);
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfTeamValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("Each team must have 20 players"));
    }

    @Test
    public void calculateTeamStrengthTest(){
        Assertions.assertEquals(0,team.calculateTeamStrength());
    }

    @Test
    public void checkIfSkatersGoaliesValid() throws Exception{
        LeagueObjectModelMocks objMocks = new LeagueObjectModelMocks();
        Boolean isTrue = team.checkIfSkatersGoaliesValid(objMocks.get20FreeAgentArrayMock());
        Assertions.assertEquals(true,isTrue);
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        team= null;
    }

}
