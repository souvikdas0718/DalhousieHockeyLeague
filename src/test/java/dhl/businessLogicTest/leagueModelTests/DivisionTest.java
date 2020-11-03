package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.factory.InitializeObjectFactory;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DivisionTest {
    InitializeObjectFactory initObj;
    IDivision division;
    IDivision divisionParameterized;
    IValidation validate;
    IPlayerStatistics playerStatistics;

    @BeforeEach()
    public void initObject() {
        initObj = new InitializeObjectFactory();
        division = initObj.createDivision();
        validate = new CommonValidation();
        List<IPlayer> playersList = new ArrayList<>();
        playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
        playersList.add(new Player("Henry", "forward", false, playerStatistics));
        playersList.add(new Player("Max", "goalie", true, playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ITeam team = new Team("Ontario", "Mathew", headCoach, playersList);
        List<ITeam> teamArrayList = new ArrayList<>();
        teamArrayList.add(team);
        divisionParameterized = new Division("Atlantic", teamArrayList);
    }

    @Test
    public void DivisionDefaultConstructorTest() {
        String divisionName = division.getDivisionName();
        Assertions.assertTrue(divisionName.length() == 0);
        Assertions.assertTrue(division.getTeams().isEmpty());
    }

    @Test
    public void DivisionTest() {
        Assertions.assertEquals("Atlantic", divisionParameterized.getDivisionName());
        Assertions.assertTrue(divisionParameterized.getTeams().size() > 0);
    }

    @Test
    public void getDivisionNameTest() {
        Assertions.assertEquals("Atlantic", divisionParameterized.getDivisionName());
    }

    @Test
    public void getTeamsTest() {
        List<ITeam> teams = division.getTeams();
        Assertions.assertEquals(0, teams.size());
    }

    @Test
    public void checkIfDivisionValidTest() throws Exception {

        Assertions.assertTrue(divisionParameterized.checkIfDivisionValid(validate));
    }

    @Test
    public void checkIfTeamNamesUniqueInDivisionTest() throws Exception {
        List<ITeam> teams = divisionParameterized.getTeams();
        List<IPlayer> playersListTeamTwo = new ArrayList<>();
        playersListTeamTwo.add(new Player("Henry", "forward", false, playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        teams.add(new Team("Ontario", "Mathew", headCoach, playersListTeamTwo));
        divisionParameterized = new Division("Atlantic", teams);
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            divisionParameterized.checkIfDivisionValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of teams inside a division must be unique"));
    }

    @AfterEach()
    public void destroyObject() {
        initObj = null;
        division = null;
    }

}
