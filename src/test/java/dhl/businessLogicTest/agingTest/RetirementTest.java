package dhl.businessLogicTest.agingTest;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.Retirement;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.agingTest.factory.AgingTestAbstractFactory;
import dhl.businessLogicTest.agingTest.mocks.AgingMock;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetirementTest {
    Retirement retirement;
    AgingAbstractFactory agingFactory;
    AgingMock agingMock;
    MockAbstractFactory mockFactory;
    ILeagueObjectModel leagueObjectModel;
    LeagueModelAbstractFactory leagueFactory;

    @BeforeEach()
    public void initObject() {
        agingMock = AgingTestAbstractFactory.instance().createAgingMock();
        mockFactory = MockAbstractFactory.instance();
        leagueFactory = LeagueModelAbstractFactory.instance();

        ISerializeLeagueObjectModel serializeModel = mockFactory.getMockSerialize();
        agingFactory = AgingAbstractFactory.instance();
        leagueObjectModel = agingMock.retirementLeagueMock();
        retirement = (Retirement) agingFactory.createRetirement(serializeModel);
        retirement.setLeagueObjectModel(leagueObjectModel);
    }

    @Test
    public void setLeagueObjectModelTest() {
        retirement.setLeagueObjectModel(leagueObjectModel);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    public void initiateRetirementTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = leagueFactory.createPlayerStatistics(5, 5, 5, 5);
        playerStatistics1.setAge(50);
        players.add(leagueFactory.createPlayer("Henry", "forward", false, playerStatistics1));
        playersSelectedToRetire.put("Ontario", players);
        List<IPlayer> freeAgentsToRetire = new ArrayList<>();

        retirement.initiateRetirement(playersSelectedToRetire, freeAgentsToRetire,leagueObjectModel);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgents.size() == 1);
    }

    @Test
    public void initiateFreeAgentRetirementTest() throws IOException, ParseException {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> players = new ArrayList<>();
        playersSelectedToRetire.put("Ontario", players);

        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = leagueFactory.createPlayerStatistics(5, 5, 5, 5);
        playerStatistics1.setAge(50);
        freeAgents.add(leagueFactory.createFreeAgent("Jack", "forward", playerStatistics1));

        retirement.initiateRetirement(playersSelectedToRetire, freeAgents,leagueObjectModel);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        List<IPlayer> freeAgentsList = leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgentsList.size() == 1);
    }

    @Test
    public void insertVeteransTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        List<IPlayer> freeAgentsToRetire = new ArrayList<>();
        retirement.insertVeterans(playersSelectedToRetire, freeAgentsToRetire);

        Assertions.assertEquals("Dhl", leagueObjectModel.getLeagueName());
    }

    @Test
    public void sortFreeAgentsByStrengthTest() {
        List<IPlayer> freeAgentsList = agingMock.getFreeAgents();
        retirement.sortFreeAgentsByStrength(freeAgentsList);
        IPlayer bestFreeAgent = freeAgentsList.get(0);
        Assertions.assertEquals("F1", bestFreeAgent.getPlayerName());
    }

    @Test
    public void removeSelectedAgentFromFreeAgentsTest() {
        List<IPlayer> freeAgents = agingMock.getFreeAgents();
        int initialSize = freeAgents.size();
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(12, 12, 12, 12);
        playerStatistics.setAge(26);
        retirement.removeSelectedAgentFromFreeAgents(freeAgents, leagueFactory.createFreeAgent("F2", "forward", playerStatistics));
        Assertions.assertTrue(freeAgents.size() < initialSize);
    }

    @Test
    public void removeRetiredPlayersFromTeamTest() {
        LeagueModelMockAbstractFactory mockFactory = LeagueModelMockAbstractFactory.instance();
        TeamMock teamMock = mockFactory.createTeamMock();
        ITeam team = teamMock.getTeam();
        int initialSize = team.getPlayers().size();

        List<String> playerNames = new ArrayList<>();
        playerNames.add("Player1");

        retirement.removeRetiredPlayersFromTeam(playerNames, team);
        List<IPlayer> players = team.getPlayers();
        Assertions.assertTrue(players.size() < initialSize);
    }

    @Test
    public void retirePLayersTest() {
        List<IPlayer> playersToRetire = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = leagueFactory.createPlayerStatistics(5, 5, 5, 5);
        playerStatistics1.setAge(35);
        playersToRetire.add(leagueFactory.createPlayer("Hash","defense",false,playerStatistics1));

        ICoach coach = leagueFactory.createCoachDefault();
        IGeneralManager generalManager = leagueFactory.createGeneralManager("Tod","normal");
        ITeam team = leagueFactory.createTeam("Ontario",generalManager,coach,playersToRetire);


        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics2 = leagueFactory.createPlayerStatistics(5, 5, 5, 5);
        playerStatistics2.setAge(25);
        freeAgents.add(leagueFactory.createFreeAgent("Jack", "forward", playerStatistics2));
        leagueObjectModel.setFreeAgents(freeAgents);

        retirement.retirePLayers(playersToRetire, team,freeAgents);
        ILeagueObjectModel leagueObjectModel = retirement.getLeagueObjectModel();
        List<IPlayer> freeAgentsList = leagueObjectModel.getFreeAgents();
    
        Assertions.assertTrue(freeAgentsList.size() == 0);
    }

    @AfterEach()
    public void destroyObject() {
        retirement = null;

    }

}
