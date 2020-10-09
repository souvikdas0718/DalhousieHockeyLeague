package dhl.leagueModelTests;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IValidation;

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

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        team= initObj.createTeam();
        validate=new CommonValidation();
        playerArrayList=new ArrayList<>();
        playerArrayList.add(new Player("Harry","forward",false));
    }

    @Test
    public void TeamDefaultConstructorTest(){
        Assertions.assertTrue(team.getTeamName().isEmpty());
        Assertions.assertTrue(team.getHeadCoach().isEmpty() );
        Assertions.assertTrue(team.getGeneralManager().isEmpty());
        Assertions.assertEquals(0,team.getPlayers().size());
    }

    @Test
    public void TeamTest(){
        team = new Team("Ontario","Mathew","henry", playerArrayList);
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
        playersList.add(new Player("Henry","forward",false));
        playersList.add(new Player("Max","goalie",false));
        team = new Team("Ontario","Mathew","henry",playersList);
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Please select captain for the team"));
    }

    @Test
    public void checkIfOneCaptainPerTeamTest(){
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",true));
        playersList.add(new Player("Max","goalie",true));
        team = new Team("Ontario","Mathew","henry",playersList);
        Exception errorMsg= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }

    @Test
    public void checkNumberOfPlayersInTeamTest(){
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",true));
        for(int i=1;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false));
        }
        team = new Team("Ontario","Mathew","henry",playersList);
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(playersList));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception{
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Max","goalie",true));
        for(int i=1;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false));
        }
        team = new Team("Ontario","Mathew","henry",playersList);
        Assertions.assertTrue(team.checkIfTeamValid(validate));
    }

    @Test
    public void checkIfTeamPlayerMoreThan20Test() throws Exception{
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Max","goalie",true));
        for(int i=0;i<20;i++){
            playersList.add(new Player("Player"+i,"forward",false));
        }
        team = new Team("Ontario","Mathew","henry",playersList);
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            team.checkIfTeamValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("Each team must have 20 players"));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        team= null;
    }

}
