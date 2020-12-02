package dhl.businessLogicTest.agingTest;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.agingTest.factory.AgingTestAbstractFactory;
import dhl.businessLogicTest.agingTest.mocks.AgingMock;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AgingTest {
    IGameConfig gameConfig;
    Aging aging;
    AgingAbstractFactory agingFactory;
    MockAbstractFactory mockFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    AgingMock agingMock;
    ILeagueObjectModel leagueObjectModel;

    @BeforeEach()
    public void initObject() {
        leagueMockFactory=LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();
        mockFactory= MockAbstractFactory.instance();
        leagueObjectModel = leagueMock.getLeagueObjectModel();
        agingMock = AgingTestAbstractFactory.instance().createAgingMock();
        gameConfig = leagueMockFactory.createGameplayConfig().getAgingGameConfig();
        agingFactory = AgingAbstractFactory.instance();
        aging = (Aging) agingFactory.createAging(gameConfig);

    }

    @Test
    public void setAverageRetirementAgeTest() {
        Assertions.assertEquals(35, aging.getAverageRetirementAge());
    }


    @Test
    public void setMaximumAgeTest() {
        Assertions.assertEquals(50, aging.getMaximumAge());
    }

    @Test
    public void ageAllPlayerTest() {
        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectModel();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    aging.agePlayers(team.getPlayers(), LocalDate.of(2020,11,14));
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player = players.get(0);
                    IPlayerStatistics playerStatistics = player.getPlayerStats();
                    Assertions.assertEquals(25, playerStatistics.getAge());
                }
            }
        }
    }

    @Test
    public void checkPlayerStatDecayTest() {
        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectModel();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player = players.get(0);
                    IPlayerStatistics playerStatistics = player.getPlayerStats();
                    LocalDate dateOfBirthday = playerStatistics.getDateOfBirth();
                    int checkingBefore = playerStatistics.getChecking();
                    if(dateOfBirthday.getMonth()== Month.NOVEMBER && dateOfBirthday.getDayOfMonth() == 14){
                        aging.agePlayers(team.getPlayers(), LocalDate.of(2020,11,14));
                        int checkingAfterAging = playerStatistics.getChecking();
                        Assertions.assertTrue(checkingAfterAging<checkingBefore);
                    }
                }
            }
        }
    }

    @Test
    public void checkLikelihoodOfRetirementTest() {
        Assertions.assertTrue(aging.checkLikelihoodOfRetirement(100));
        Assertions.assertFalse(aging.checkLikelihoodOfRetirement(0));
    }

    @Test
    public void selectPlayersToRetireMaxAgeTest() {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        ITeam team = agingMock.teamWithPlayersAtMaxAge();
         aging.selectPlayersToRetire(team,playersSelectedToRetire);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectPlayersToRetireGreaterThanAvgTest() {
        Random random = aging.getRandomGenerator();
        random.setSeed(1);
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>() ;
        ITeam team = agingMock.teamWithPlayersMoreThanAvg();

        aging.selectPlayersToRetire(team,playersSelectedToRetire);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectPlayersToRetireLessThanAvgTest() {
        Random random = aging.getRandomGenerator();
        random.setSeed(5000);
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        ITeam team = agingMock.teamWithPlayersLessThanAvg();
        playersSelectedToRetire = aging.selectPlayersToRetire(team,playersSelectedToRetire);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(1, playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectFreeAgentsToRetireTest() {
        List<IPlayer> agentsSelectedToRetire ;
        leagueObjectModel = agingMock.getFreeAgentsInLeague();
        agentsSelectedToRetire = aging.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());

        Assertions.assertEquals(1, agentsSelectedToRetire.size());
    }

    @Test
    public void playerNotNullTest(){
        PlayerMock playerMock = leagueMockFactory.createPlayerMock();
        IPlayer player = playerMock.getPlayer();
        Assertions.assertTrue(aging.playerNotNull(player));
    }

    @Test
    public void playerNullTest(){
        Assertions.assertFalse(aging.playerNotNull(null));
    }

    @AfterEach()
    public void destroyObject() {
        leagueMock = null;
        gameConfig = null;
        aging = null;

    }

}
