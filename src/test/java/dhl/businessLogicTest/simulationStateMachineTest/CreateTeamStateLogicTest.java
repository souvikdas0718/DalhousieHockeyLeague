package dhl.businessLogicTest.simulationStateMachineTest;

import dhl.mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.LeagueObjectModelInput;
import dhl.businessLogic.leagueModel.LeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.states.StatesAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ICreateTeamStateLogic;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.SerializeDeserializeAbstractFactory;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTeamStateLogicTest {

    GameContext ourGame;
    StatesAbstractFactory statesAbstractFactory;
    ICreateTeamStateLogic testClassObject;
    LeagueObjectModelMocks leagueObjectModelMocks;
    private ILeagueObjectModel inMemoryLeague;
    LeagueModelMockAbstractFactory factory;
    IGeneralManager generalManager;

    @BeforeEach
    public void initObject() {
        ourGame = new GameContext();
        statesAbstractFactory = StatesAbstractFactory.instance();
        testClassObject = statesAbstractFactory.createCreateTeamStateLogic();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        inMemoryLeague = leagueObjectModelMocks.getLeagueObjectMock();
        factory = LeagueModelMockAbstractFactory.instance();
        generalManager = factory.createManagerMock().getManager();
    }

    @Test
    public void saveleagueObjectTest() throws Exception {
        ILeagueObjectModelValidation validation = new LeagueObjectModelValidation();
        ITeam team = new Team("Ontario1", generalManager, leagueObjectModelMocks.getSingleCoach(), new ArrayList<>());
        SerializeDeserializeAbstractFactory serializeDeserializeAbstractFactory = SerializeDeserializeAbstractFactory.instance();
        ISerializeLeagueObjectModel serializeLeagueObjectModel = serializeDeserializeAbstractFactory.createSerializeLeagueObjectModel("src/test/java/dhl/mocks");
        LeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput(inMemoryLeague.getLeagueName(), "Western", "Atlantic", team, validation, serializeLeagueObjectModel);

        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();
        objLeagueObjectModel = testClassObject.saveleagueObject(ourGame, inMemoryLeague, leagueObjectModelInput);

        Assertions.assertEquals("Dhl", objLeagueObjectModel.getLeagueName());
    }

    @Test
    public void findConferenceTest() {
        IConference conferenceTest = testClassObject.findConference(leagueObjectModelMocks.getConferenceArrayMock(), "Mock Conference1");

        assertTrue(conferenceTest.getConferenceName().equals("Mock Conference1"));

        conferenceTest = testClassObject.findConference(leagueObjectModelMocks.getConferenceArrayMock(), "Wrong Conference1");

        assertTrue(conferenceTest == null);
    }

    @Test
    public void findDivisionTest() {
        IDivision divisionTest = testClassObject.findDivision(leagueObjectModelMocks.getDivisionArrayMock(), "Mock Division2");
        assertTrue(divisionTest.getDivisionName().equals("Mock Division2"));

        divisionTest = testClassObject.findDivision(leagueObjectModelMocks.getDivisionArrayMock(), "Wrong DivisionName");

        assertTrue(divisionTest == null);
    }

    @Test
    public void findFreeAgent() {
        IPlayer freeAgentTest = testClassObject.findFreeAgent(leagueObjectModelMocks.getFreeAgentArrayMock(), "Mock Free Agent 1");
        assertTrue(freeAgentTest.getPlayerName().equals("Mock Free Agent 1"));

        freeAgentTest = testClassObject.findFreeAgent(leagueObjectModelMocks.getFreeAgentArrayMock(), "Wrong Free Agent");
        assertTrue(freeAgentTest == null);
    }

    @Test
    public void createNewTeamObjectTest() throws Exception {
        ITeam teamWithoutPlayers = new Team("testTeam", generalManager, leagueObjectModelMocks.getSingleCoach(), new ArrayList<>());
        ITeam newlyCreatedTeam = new Team();

        newlyCreatedTeam = testClassObject.createNewTeamObject(leagueObjectModelMocks.get20FreeAgentArrayMock(), teamWithoutPlayers, "Henry1");

        Assertions.assertEquals(20, newlyCreatedTeam.getPlayers().size());
    }

    @Test
    public void validateCorrectInputFreeAgents() throws Exception {
        String inputFreeAgents = "Henry0,Henry1,Henry2,Henry3,Henry4,Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19,Henry20,Henry21,Henry22,Henry23,Henry24,Henry25,Henry26,Henry27,Henry28,Henry29";
        ArrayList<IPlayer> freeAgent = new ArrayList<>();
        freeAgent = testClassObject.validateInputFreeAgents(inputFreeAgents, leagueObjectModelMocks.get30FreeAgentArrayMock());

        Assertions.assertNotNull(freeAgent);
        Assertions.assertEquals(30, freeAgent.size());
    }

    @Test
    public void findGeneralManager() {
        IGeneralManager generalManager = testClassObject.findGeneralManager(leagueObjectModelMocks.getManagers(), "Karen Potam");
        assertTrue(generalManager.getGeneralManagerName().equals("Karen Potam"));

        IGeneralManager wronggeneralManager = testClassObject.findGeneralManager(leagueObjectModelMocks.getManagers(), "Wrong generalmanager");
        assertTrue(wronggeneralManager == null);
    }

    @Test
    public void findCoach() {
        String coach = testClassObject.findCoach(leagueObjectModelMocks.getCoaches(), "Todd McLellan");
        assertTrue(coach.equals("Todd McLellan"));

        String wrongcoach = testClassObject.findCoach(leagueObjectModelMocks.getCoaches(), "Wrong coach");
        assertTrue(wrongcoach == null);
    }

    @Test
    public void validateWrongInputFreeAgents() throws Exception {
        String wrongInputFreeAgents = "Henry101,Henry1,Henry2,Henry3,Henry4,Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19,Henry21,Henry22,Henry23,Henry24,Henry25,Henry26,Henry27,Henry28,Henry29,Henry30";

        ArrayList<IPlayer> players = testClassObject.validateInputFreeAgents(wrongInputFreeAgents, leagueObjectModelMocks.get20FreeAgentArrayMock());
        Assertions.assertNull(players);
    }

    @Test
    public void validate30InputFreeAgents() throws Exception {
        String inputFreeAgents = "Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19";
        ArrayList<IPlayer> players = testClassObject.validateInputFreeAgents(inputFreeAgents, leagueObjectModelMocks.get30FreeAgentArrayMock());
        Assertions.assertEquals(players.size(), 0);
    }
}
