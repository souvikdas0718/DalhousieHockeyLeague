package dhl.businessLogicTest.leagueModelTests.factory;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LeagueModelFactoryTest {
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach
    public void initialize()  {
        leagueFactory= LeagueModelAbstractFactory.instance();

    }

    @Test
    public void createConferenceTest() {
        IConference conference = leagueFactory.createConference("Eastern", new ArrayList<>());
        Assertions.assertEquals("Eastern",conference.getConferenceName());
    }

    @Test
    public void createDivisionTest() {
        IDivision division = leagueFactory.createDivision("Atlantic", new ArrayList<>());
        Assertions.assertEquals("Atlantic",division.getDivisionName());
    }

    @Test
    public void createCoachTest() {
        ICoach coach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.2, 0.5, 1.0);
        Assertions.assertEquals("Todd McLellan",coach.getCoachName());
    }

    @Test
    public void createGeneralManagerTest() {
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew Jacob","normal");
        Assertions.assertEquals("Mathew Jacob",manager.getGeneralManagerName());
    }

    @Test
    public void createTeamTest() {
        ICoach coach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        ITeam team = leagueFactory.createTeam("Ontario", manager, coach, new ArrayList<>());
        Assertions.assertEquals("Ontario",team.getTeamName());

    }

    @Test
    public void createPlayerStatisticsTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics( 10, 10, 10, 10);
        Assertions.assertEquals(10,playerStatistics.getSkating());
    }

    @Test
    public void createPlayerTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics( 10, 10, 10, 10);
        IPlayer player = leagueFactory.createPlayer("Harry", "forward", false, playerStatistics);
        Assertions.assertEquals("Harry",player.getPlayerName());
    }

    @Test
    public void createFreeAgentTest() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics( 10, 10, 10, 10);;
        IPlayer player = leagueFactory.createFreeAgent("Noah", "forward", playerStatistics);
        Assertions.assertEquals("Noah",player.getPlayerName());
    }

    @Test
    public void createLeagueObjectModelInput() {
        ICoach coach = leagueFactory.createCoach("Andrew", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Rob", "normal");
        ITeam newTeam = leagueFactory.createTeam("Ontario", manager, coach, new ArrayList<>());
        ILeagueObjectModelInput leagueObjectModelInput =leagueFactory.createLeagueObjectModelInput("LeagueName", "ConferenceName", "DivisionName",newTeam, null);
        Assertions.assertEquals("LeagueName",leagueObjectModelInput.getLeagueName());
    }

    @Test
    public void createLeagueObjectModelDefault() {
        Assertions.assertNotNull(leagueFactory.createDefaultLeagueObjectModel());
    }

    @Test
    public void createConferenceDefault() {
        Assertions.assertNotNull(leagueFactory.createConferenceDefault());
    }

    @Test
    public void createDivisionDefault() {
        Assertions.assertNotNull(leagueFactory.createDivisionDefault());
    }

    @Test
    public void createTeamDefault() {
        Assertions.assertNotNull(leagueFactory.createTeamDefault());
    }

    @Test
    public void createPlayerDefault() {
        Assertions.assertNotNull(leagueFactory.createPlayerDefault());
    }

    @Test
    public void createFreeAgentDefault() {
        Assertions.assertNotNull(leagueFactory.createFreeAgentDefault());
    }

    @Test
    public void createPlayerDraft() {
        Assertions.assertNotNull(leagueFactory.createPlayerDefault());
    }

    @Test
    public void createCommonValidation() {
        Assertions.assertNotNull(leagueFactory.createCommonValidation());
    }

}
