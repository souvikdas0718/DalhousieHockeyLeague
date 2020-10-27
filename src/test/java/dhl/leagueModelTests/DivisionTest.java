package dhl.leagueModelTests;

import dhl.leagueModel.*;
import dhl.factory.InitializeObjectFactory;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class DivisionTest {
    InitializeObjectFactory initObj;
    IDivision division;
    IDivision divisionParameterized;
    IValidation validate;
    IPlayerStatistics playerStatistics;

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        division= initObj.createDivision();
        validate=new CommonValidation();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playerStatistics =new PlayerStatistics(20,10,10,10,10);
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Ontario","Mathew",headCoach,playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        divisionParameterized = new Division("Atlantic",teamArrayList);
    }

    @Test
    public void DivisionDefaultConstructorTest(){
        Assertions.assertTrue(division.getDivisionName().isEmpty());
        Assertions.assertTrue(division.getTeams().isEmpty());
    }

    @Test
    public void DivisionTest(){
        Assertions.assertEquals("Atlantic",divisionParameterized.getDivisionName());
        Assertions.assertTrue(divisionParameterized.getTeams().size()>0);
    }

    @Test
    public void getDivisionNameTest(){
        division.setDivisionName("Atlantic");
        Assertions.assertEquals("Atlantic",division.getDivisionName());
    }

    @Test
    public void setDivisionNameTest(){
        division.setDivisionName("Pacific");
        Assertions.assertEquals("Pacific",division.getDivisionName());
    }

    @Test
    public void getTeamsTest(){
        ArrayList<ITeam> teamsArrayList=new ArrayList<>();
        division.getTeams();
        Assertions.assertEquals(0,division.getTeams().size());
    }

    @Test
    public void setTeamsTest(){
        ArrayList<ITeam> teamsArrayList=new ArrayList<>();
        division.setTeams(teamsArrayList);
        Assertions.assertEquals(0,division.getTeams().size());
    }

    @Test
    public void checkIfDivisionValidTest() throws Exception{

        Assertions.assertTrue(divisionParameterized.checkIfDivisionValid(validate));
    }

    @Test
    public void checkIfTeamNamesUniqueInDivisionTest() throws Exception{
        ArrayList<ITeam> teams =divisionParameterized.getTeams();
        ArrayList<IPlayer> playersListTeamTwo=new ArrayList<>();
        playersListTeamTwo.add(new Player("Henry","forward",false,playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        teams.add(new Team("Ontario","Mathew",headCoach,playersListTeamTwo));
        divisionParameterized.setTeams(teams);
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            divisionParameterized.checkIfDivisionValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of teams inside a division must be unique"));
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        division= null;
    }

}
