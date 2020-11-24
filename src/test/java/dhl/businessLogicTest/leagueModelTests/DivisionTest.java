package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.DivisionMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DivisionTest {
    IDivision division;
    IDivision divisionParameterized;
    IValidation validate;
    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;
    DivisionMock divisionMock;

    @BeforeEach()
    public void initObject() {
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        division = factory.createDivisionDefault();
        validate = factory.createCommonValidation();
        divisionMock = mockFactory.createDivisionMock();
        divisionParameterized = divisionMock.getDivision();
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
        divisionParameterized = divisionMock.getDivisionWithSameTeamName();
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            divisionParameterized.checkIfDivisionValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of teams inside a division must be unique"));
    }

    @AfterEach()
    public void destroyObject() {
        division = null;
    }

}
