package dhl.businessLogicTest.AgingTest;

import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.InputOutput.importJson.SerializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogicTest.leagueModelTests.PlayerDBMock;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.aging.Retirement;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetirementTest {
    Retirement retirement;
    LeagueObjectModelMocks leagueMock;
    IPlayerDB playerDBMock;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        ISerializeLeagueObjectModel serializeModel = new SerializeLeagueObjectModel();
        retirement = new Retirement(serializeModel, leagueMock.getLeagueObjectMock());
    }

    @Test
    public void setLeagueObjectModelTest() {
        retirement.setLeagueObjectModel(leagueMock.getLeagueObjectMock());
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    public void initiateRetirementTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 5, 5, 5, 5);
        players.add(new Player("Henry", "forward", false, playerStatistics1));
        playersSelectedToRetire.put("Ontario", players);
        List<IPlayer> freeAgentsToRetire = new ArrayList<>();

        retirement.initiateRetirement(playersSelectedToRetire, freeAgentsToRetire);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgents.size() == 1);
    }

    @Test
    public void initiateFreeAgentRetirementTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> players = new ArrayList<>();
        playersSelectedToRetire.put("Ontario", players);

        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = new PlayerStatistics(50, 5, 5, 5, 5);
        freeAgents.add(new FreeAgent("Jack", "forward", playerStatistics1));

        retirement.initiateRetirement(playersSelectedToRetire, freeAgents);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        List<IPlayer> freeAgentsList = leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgentsList.size() == 1);
    }

    @Test
    public void insertVeteransTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> freeAgentsToRetire = new ArrayList<>();
        retirement.insertVeterans(playersSelectedToRetire, freeAgentsToRetire);
        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectMock();
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    public void sortFreeAgentsByStrengthTest() {
        List<IPlayer> freeAgentsList = new ArrayList<>();

        IPlayerStatistics freeAgentStatistics1 = new PlayerStatistics(20, 20, 15, 15, 15);
        IPlayerStatistics freeAgentStatistics2 = new PlayerStatistics(35, 13, 14, 12, 11);
        IPlayerStatistics freeAgentStatistics3 = new PlayerStatistics(20, 1, 3, 3, 3);
        IPlayerStatistics freeAgentStatistics4 = new PlayerStatistics(25, 6, 3, 3, 5);
        IPlayerStatistics freeAgentStatistics5 = new PlayerStatistics(25, 1, 2, 15, 13);

        freeAgentsList.add(new FreeAgent("F2", "goalie", freeAgentStatistics2));
        freeAgentsList.add(new FreeAgent("F3", "goalie", freeAgentStatistics3));
        freeAgentsList.add(new FreeAgent("F1", "goalie", freeAgentStatistics1));
        freeAgentsList.add(new FreeAgent("F4", "goalie", freeAgentStatistics4));
        freeAgentsList.add(new FreeAgent("F5", "goalie", freeAgentStatistics5));

        retirement.sortFreeAgentsByStrength(freeAgentsList);
        IPlayer bestFreeAgent = freeAgentsList.get(0);
        Assertions.assertEquals("F1", bestFreeAgent.getPlayerName());
    }

    @Test
    public void removeSelectedAgentFromFreeAgentsTest() {
        List<IPlayer> freeAgents = leagueMock.getFreeAgentArrayMock();
        retirement.removeSelectedAgentFromFreeAgents(freeAgents, new Player("Mock Free Agent 2", "forward", new PlayerStatistics(26, 12, 12, 12, 12)));
        Assertions.assertTrue(freeAgents.size() == 1);
    }

    @Test
    public void removeRetiredPlayersFromTeamTest() {
        ITeam team = leagueMock.getTeamObjectMock();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Mock Player");

        retirement.removeRetiredPlayersFromTeam(playerNames, team);
        List<IPlayer> players = team.getPlayers();
        Assertions.assertTrue(players.size() == 1);
    }

    @AfterEach()
    public void destroyObject() {
        leagueMock = null;
        retirement = null;

    }

}
