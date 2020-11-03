package dhl.businessLogicTest.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.factory.InitializeObjectFactory;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
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
    LeagueObjectModelMocks leagueMock;

    @BeforeEach()
    public void initObject() {
        initObj = new InitializeObjectFactory();
        validate = new CommonValidation();
        playerArrayList = new ArrayList<>();
        playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
        playerArrayList.add(new Player("Harry", "forward", false, playerStatistics));
        headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        team = new Team("Ontario", "Mathew", headCoach, playerArrayList);
        leagueMock = new LeagueObjectModelMocks();
    }

    @Test
    public void TeamDefaultConstructorTest() {
        team = new Team();
        String teamName = team.getTeamName();
        Assertions.assertTrue(teamName.length() == 0);
        ICoach coach = team.getHeadCoach();
        String coachName = coach.getCoachName();
        Assertions.assertTrue(coachName.length() == 0);
        String generalManagerName = team.getGeneralManager();
        Assertions.assertTrue(generalManagerName.length() == 0);
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
        Assertions.assertEquals("Mathew", team.getGeneralManager());
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
    public void checkIfOneCaptainPerTeamErrorTest() {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Henry", "forward", false, playerStatistics));
        playersList.add(new Player("Max", "goalie", false, playerStatistics));
        team = new Team("Ontario", "Mathew", headCoach, playersList);
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
        team = new Team("Ontario", "Mathew", headCoach, playersList);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            team.checkIfOneCaptainPerTeam(playersList);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("There can be only one captain per team"));
    }

    @Test
    public void checkNumberOfPlayersInTeamTest() {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Henry", "forward", true, playerStatistics));
        for (int i = 1; i < 20; i++) {
            playersList.add(new Player("Player" + i, "forward", false, playerStatistics));
        }
        team = new Team("Ontario", "Mathew", headCoach, playersList);
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(playersList));
    }

    @Test
    public void checkIfTeamValidTest() throws Exception {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Max", "goalie", true, playerStatistics));
        for (int i = 1; i < 20; i++) {
            playersList.add(new Player("Player" + i, "forward", false, playerStatistics));
        }
        team = new Team("Ontario", "Mathew", headCoach, playersList);
        Assertions.assertTrue(team.checkIfTeamValid(validate));
    }

    @Test
    public void checkIfTeamPlayerMoreThan20Test() throws Exception {
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Max", "goalie", true, playerStatistics));
        for (int i = 0; i < 20; i++) {
            playersList.add(new Player("Player" + i, "forward", false, playerStatistics));
        }
        team = new Team("Ontario", "Mathew", headCoach, playersList);
        Exception error = Assertions.assertThrows(Exception.class, () -> {
            team.checkIfTeamValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("Each team must have 20 players"));
    }

    @Test
    public void checkIfSkatersGoaliesValid() {
        team = new Team("Ontario", "Mathew", headCoach, leagueMock.get20FreeAgentArrayMock());
        Boolean isValid = team.checkIfSkatersGoaliesValid();
        Assertions.assertEquals(true, isValid);
    }

    @Test
    public void checkIfSkatersGoaliesInValid() {
        List<IPlayer> selectedPlayers = leagueMock.get20FreeAgentArrayMock();
        selectedPlayers.remove(19);
        team = new Team("Ontario", "Mathew", headCoach, selectedPlayers);
        Assertions.assertFalse(team.checkIfSkatersGoaliesValid());
    }

    @Test
    public void calculateTeamStrength() {
        playerArrayList.add(new Player("Jared", "defense", false, playerStatistics));
        team = new Team("Ontario", "Mathew", headCoach, playerArrayList);
        Assertions.assertEquals(50, team.calculateTeamStrength());
    }

    @AfterEach()
    public void destroyObject() {
        initObj = null;
        team = null;
    }

}
