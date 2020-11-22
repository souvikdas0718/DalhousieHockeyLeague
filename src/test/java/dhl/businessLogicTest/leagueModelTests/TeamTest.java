package dhl.businessLogicTest.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.factory.InitializeObjectFactory;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TeamTest {
    InitializeObjectFactory initObj;
    IValidation validate;
    Team team;
    List<IPlayer> playerArrayList;
    IPlayerStatistics playerStatistics;
    ICoach headCoach;
    IGeneralManager manager;
    LeagueObjectModelMocks leagueMock;

    @BeforeEach()
    public void initObject() {
        initObj = new InitializeObjectFactory();
        validate = new CommonValidation();
        playerArrayList = new ArrayList<>();
        playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
        playerArrayList.add(new Player("Harry", "forward", false, playerStatistics));
        headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        manager = new GeneralManager("Mathew", "normal");
        team = new Team("Ontario", manager, headCoach, playerArrayList);
        leagueMock = new LeagueObjectModelMocks();
    }

    @Test
    public void TeamDefaultConstructorTest() {
        team = new Team();
        String teamName = team.getTeamName();
        Assertions.assertEquals(0,teamName.length() );
        ICoach coach = team.getHeadCoach();
        String coachName = coach.getCoachName();
        Assertions.assertEquals(0,coachName.length() );
        String generalManagerName = team.getGeneralManager().getGeneralManagerName();
        Assertions.assertEquals(0,generalManagerName.length() );
        Assertions.assertEquals(0, team.getPlayers().size());
    }

    @Test
    public void TeamTest() {
        Assertions.assertEquals("Ontario", team.getTeamName());
    }

    @Test
    public void getTeamNameTest() {
        Assertions.assertEquals("Ontario", team.getTeamName());
    }


    @Test
    public void getGeneralManagerTest() {
        Assertions.assertEquals("Mathew", team.getGeneralManager().getGeneralManagerName());
    }


    @Test
    public void getHeadCoachTest() {
        ICoach coach = team.getHeadCoach();
        Assertions.assertEquals("Todd McLellan", coach.getCoachName());

    }

    @Test
    public void getPlayersTest() {
        Assertions.assertTrue(team.getPlayers().size() > 0);

    }

    @Test
    public void setLossPointTest() {
        team.setLossPoint(5);
        Assertions.assertEquals(5, team.getLossPoint());
    }

    @Test
    public void setTeamPointTest() {
        team.setTeamPoint(15);
        Assertions.assertEquals(15, team.getTeamPoint());
    }

    @Test
    public void setActiveRosterTest(){
        List<IPlayer> player =leagueMock.getTeamPlayers();
        Team testTeam = new Team("Ontario", manager, headCoach, player);
        Assertions.assertEquals(20, testTeam.getActiveRoster().size());
    }

    @Test
    public void setInactiveRosterTest(){
        Team testTeam = new Team("Ontario", manager, headCoach, leagueMock.getTeamPlayers());
        Assertions.assertEquals(10, testTeam.getInactiveRoster().size());
    }

    @Test
    public void sortPlayersInTeamByStrengthTest(){
        team = new Team("Ontario", manager, headCoach, leagueMock.getTeamPlayers());
        List<IPlayer> players = leagueMock.getTeamPlayers();
        team.sortPlayersInTeamByStrength(players);
        IPlayer firstPlayer=players.get(0);
        IPlayer lastPlayer=players.get(players.size()-1);
       Assertions.assertTrue(firstPlayer.getPlayerStrength()>lastPlayer.getPlayerStrength());
    }

    @Test
    public void checkIfOneCaptainPerTeamErrorTest() {
        List<IPlayer> playersList = leagueMock.getTeamPlayers();
        playersList.remove(0);
        team = new Team("Ontario", manager, headCoach, playersList);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Please select captain for the team"));
    }

    @Test
    public void checkIfOneCaptainPerTeamTest() {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Henry", "forward", true, playerStatistics));
        playersList.add(new Player("Max", "goalie", true, playerStatistics));
        team = new Team("Ontario", manager, headCoach, playersList);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }

    @Test
    public void checkNumberOfPlayersInTeamTest() {
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(leagueMock.getTeamPlayers()));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception {
        team = new Team("Ontario", manager, headCoach, leagueMock.getTeamPlayers());
        Assertions.assertTrue(team.checkIfTeamValid(validate));
    }

    @Test
    public void checkIfTeamPlayerMoreThan20Test() throws Exception {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Max", "goalie", true, playerStatistics));
        for (int i = 0; i < 30; i++) {
            playersList.add(new Player("Player" + i, "forward", false, playerStatistics));
        }
        team = new Team("Ontario", manager, headCoach, playersList);
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            team.checkIfTeamValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("Each team must have 30 players"));
    }

    @Test
    public void calculateTeamStrength() {
        playerArrayList.add(new Player("Jared", "defense", false, playerStatistics));
        team = new Team("Ontario", manager, headCoach, leagueMock.getTeamPlayers());
        Assertions.assertTrue( team.calculateTeamStrength()>0);
    }

    @Test
    public void checkTeamPlayersCountValid() {
        team = new Team("Ontario", manager, headCoach, leagueMock.getTeamPlayers());
        Boolean isValid = team.checkTeamPlayersCount();
        Assertions.assertEquals(true, isValid);
    }

    @Test
    public void checkTeamPlayersCountInValid() {
        List<IPlayer> selectedPlayers = leagueMock.get20FreeAgentArrayMock();
        selectedPlayers.remove(19);
        team = new Team("Ontario", manager, headCoach, selectedPlayers);
        Assertions.assertFalse(team.checkTeamPlayersCount());
    }


    @AfterEach()
    public void destroyObject() {
        initObj = null;
        team = null;
    }

}
