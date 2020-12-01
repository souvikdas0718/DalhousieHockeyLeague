package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.CommonValidation;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommonValidationTest {
    IValidation commonValidation;
    TeamMock teamMock;
    LeagueModelMockAbstractFactory mockFactory;

    @BeforeEach()
    public void initObject() {
        LeagueModelAbstractFactory leagueFactory= LeagueModelAbstractFactory.instance();
        commonValidation = leagueFactory.createCommonValidation();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        teamMock = mockFactory.createTeamMock();
    }

    @Test
    public void isStringEmptyTest() {
        Assertions.assertTrue(commonValidation.isStringEmpty("", "Team"));
    }

    @Test
    public void isListEmptyTest() {

        List<IPlayer> players = new ArrayList<>();
        Assertions.assertTrue(commonValidation.isListEmpty(players, "players"));
    }

    @Test
    public void isListNotEmptyTest()  {

        commonValidation.isListEmpty(teamMock.getTeamPlayers(), "players");
        Assertions.assertTrue(teamMock.getTeamPlayers().size() > 0);
    }

    @AfterEach()
    public void destroyObject() {
        commonValidation = null;
    }

}
