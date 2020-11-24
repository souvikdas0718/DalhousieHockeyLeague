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
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            commonValidation.isStringEmpty("", "Team");
        });
        Assertions.assertTrue(error.getMessage().contains("Team name cannot be empty"));
    }

    @Test
    public void isListEmptyTest() {
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            List<IPlayer> players = new ArrayList<>();
            commonValidation.isListEmpty(players, "players");
        });
        Assertions.assertTrue(error.getMessage().contains("Please add players"));
    }

    @Test
    public void isListNotEmptyTest() throws Exception {

        commonValidation.isListEmpty(teamMock.getTeamPlayers(), "players");
        Assertions.assertTrue(teamMock.getTeamPlayers().size() > 0);
    }

    @AfterEach()
    public void destroyObject() {
        commonValidation = null;
    }

}
