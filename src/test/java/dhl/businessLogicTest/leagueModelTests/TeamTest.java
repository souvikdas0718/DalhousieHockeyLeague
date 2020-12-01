package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TeamTest {
    LeagueModelMockAbstractFactory mockFactory;
    LeagueModelAbstractFactory factory;
    IValidation validate;
    Team team;
    TeamMock teamMock;

    @BeforeEach()
    public void initObject() {
        mockFactory = LeagueModelMockAbstractFactory.instance();
        factory = LeagueModelAbstractFactory.instance();
        validate = factory.createCommonValidation();
        teamMock = mockFactory.createTeamMock();
        team = (Team) teamMock.getTeam();
    }

    @Test
    public void TeamDefaultConstructorTest() {
        team = (Team) factory.createTeamDefault();
        String teamName = team.getTeamName();
        Assertions.assertEquals(0,teamName.length() );
        ICoach coach = team.getHeadCoach();
        String coachName = coach.getCoachName();
        Assertions.assertEquals(0,coachName.length() );
        IGeneralManager manager = team.getGeneralManager();
        Assertions.assertEquals(0,manager.getGeneralManagerName().length() );
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
    public void getTOTALTEAMSIZETest() {
        Assertions.assertEquals(30,team.getTotalTeamSize());
    }

    @Test
    public void getTOTALGOALIESTest() {
        Assertions.assertEquals(4,team.getTotalGoalies());
    }

    @Test
    public void getTOTALFORWARDSTest() {
        Assertions.assertEquals(16,team.getTotalForwards());
    }
    @Test
    public void getTOTALDEFENSETest() {
        Assertions.assertEquals(10,team.getTotalDefense());
    }

    @Test
    public void setActiveRosterTest(){
        Assertions.assertEquals(20, team.getActiveRoster().size());
    }

    @Test
    public void setInactiveRosterTest(){
        Assertions.assertEquals(10, team.getInactiveRoster().size());
    }

    @Test
    public void sortPlayersInTeamByStrengthTest(){
        List<IPlayer> players = teamMock.getTeamPlayers();
        team.sortPlayersInTeamByStrength(players);
        IPlayer firstPlayer=players.get(0);
        IPlayer lastPlayer=players.get(players.size()-1);
       Assertions.assertTrue(firstPlayer.getPlayerStrength()>lastPlayer.getPlayerStrength());
    }

    @Test
    public void checkIfOneCaptainPerTeamErrorTest() {
        List<IPlayer> playersList = teamMock.getTeamPlayers();
        playersList.remove(0);
        Assertions.assertFalse( team.checkIfOneCaptainPerTeam(playersList));
    }

    @Test
    public void checkIfOneCaptainPerTeamValidTest() {
        List<IPlayer> playersList = teamMock.getTeamPlayers();
        Assertions.assertTrue( team.checkIfOneCaptainPerTeam(playersList));
    }

    @Test
    public void checkIfOneCaptainPerTeamTest() {
        List<IPlayer> playersList = new ArrayList<>();
        PlayerMock playerMock = mockFactory.createPlayerMock();
        for(int i=0;i<2;i++){
            playersList.add(playerMock.getPlayerCaptain());
        }

        Assertions.assertFalse(team.checkIfOneCaptainPerTeam(playersList));
    }

    @Test
    public void checkNumberOfPlayersInTeamTest() {
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(teamMock.getTeamPlayers()));
    }

    @Test
    public void checkIfTeamValidTest() {
        Assertions.assertTrue(team.checkIfSizeOfTeamValid(team.getPlayers()));
    }

    @Test
    public void checkIfTeamPlayerMoreThan30Test() {
       team = (Team) teamMock.getInvalidSizeTeam();
        Assertions.assertFalse(team.checkIfSizeOfTeamValid(team.getPlayers()));
    }

    @Test
    public void calculateTeamStrength() {
        Assertions.assertTrue( team.calculateTeamStrength()>0);
    }

    @Test
    public void checkTeamPlayersCountValid() {
        boolean isValid = team.checkTeamPlayersCount();
        Assertions.assertEquals(true, isValid);
    }

    @Test
    public void checkTeamPlayersCountInValid() {
        team = (Team) teamMock.getInvalidSizeTeam();
        Assertions.assertFalse(team.checkTeamPlayersCount());
    }

    @AfterEach()
    public void destroyObject() {
        team = null;
    }

}
