package dhl.leagueModelTests;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.Division;
import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IValidation;
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

    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        division= initObj.createDivision();
        validate=new CommonValidation();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false));
        playersList.add(new Player("Max","goalie",true));
        ITeam team = new Team("Ontario","Mathew","henry",playersList);
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

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        division= null;
    }

}
