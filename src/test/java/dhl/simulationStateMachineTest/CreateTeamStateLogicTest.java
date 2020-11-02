package dhl.simulationStateMachineTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelTests.MockDatabase;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.CreateTeamStateLogic;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTeamStateLogicTest {

    GameContext ourGame;
    CreateTeamStateLogic testClassObject;
    LeagueObjectModelMocks leagueObjectModelMocks;
    private ILeagueObjectModel inMemoryLeague;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new CreateTeamStateLogic();
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        inMemoryLeague = leagueObjectModelMocks.getLeagueObjectMock();
    }

    @Test
    public void saveleagueObjectTest() throws Exception {
        ILeagueObjectModelValidation validation = new LeagueObjectModelValidation();
        ITeam team= new Team("Ontario1","Mathew1",leagueObjectModelMocks.getSingleCoach(),new ArrayList<>());
        ILeagueObjectModelDB leagueObjectModelDB=new MockDatabase();
        LeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput(inMemoryLeague.getLeagueName(), "Western", "Atlantic", team,validation, leagueObjectModelDB);

        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();
        objLeagueObjectModel = testClassObject.saveleagueObject( ourGame,inMemoryLeague,leagueObjectModelInput);

        Assertions.assertEquals("Dhl",objLeagueObjectModel.getLeagueName());

        inMemoryLeague = null;
        objLeagueObjectModel = testClassObject.saveleagueObject( ourGame,inMemoryLeague,leagueObjectModelInput);

        Assertions.assertEquals(false, ourGame.isGameInProgress());
    }

    @Test
    public void findConferenceTest(){
        IConference conferenceTest = testClassObject.findConference(leagueObjectModelMocks.getConferenceArrayMock() , "Mock Conference1");

        assertTrue(conferenceTest.getConferenceName().equals("Mock Conference1"));

        conferenceTest = testClassObject.findConference(leagueObjectModelMocks.getConferenceArrayMock() , "Wrong Conference1");

        assertTrue(conferenceTest == null);
    }

    @Test
    public void findDivisionTest(){
        IDivision divisionTest = testClassObject.findDivision(leagueObjectModelMocks.getDivisionArrayMock() , "Mock Division2");
        assertTrue(divisionTest.getDivisionName().equals("Mock Division2"));

        divisionTest = testClassObject.findDivision(leagueObjectModelMocks.getDivisionArrayMock() , "Wrong DivisionName");

        assertTrue(divisionTest == null);
    }

    @Test
    public void findFreeAgent(){
        IPlayer freeAgentTest = testClassObject.findFreeAgent(leagueObjectModelMocks.getFreeAgentArrayMock() , "Mock Free Agent 1");
        assertTrue(freeAgentTest.getPlayerName().equals("Mock Free Agent 1"));

        freeAgentTest = testClassObject.findFreeAgent(leagueObjectModelMocks.getFreeAgentArrayMock() , "Wrong Free Agent");
        assertTrue(freeAgentTest == null);
    }

    @Test
    public void createNewTeamObjectTest() throws Exception {
        ITeam teamWithoutPlayers= new Team("testTeam", "testGenManager", leagueObjectModelMocks.getSingleCoach(),new ArrayList<>());
        ITeam newlyCreatedTeam= new Team();

        newlyCreatedTeam = testClassObject.createNewTeamObject(leagueObjectModelMocks.get20FreeAgentArrayMock(),teamWithoutPlayers, "Henry1");

        Assertions.assertEquals(20,newlyCreatedTeam.getPlayers().size());
    }

    @Test
    public void validateCorrectInputFreeAgents() throws Exception{
        String inputFreeAgents = "Henry0,Henry1,Henry2,Henry3,Henry4,Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19";
        ArrayList<IPlayer> freeAgent = new ArrayList<>();
        freeAgent = testClassObject.validateInputFreeAgents(inputFreeAgents, leagueObjectModelMocks.get20FreeAgentArrayMock());

        Assertions.assertNotNull(freeAgent);
        Assertions.assertEquals(20,freeAgent.size());
    }

    @Test
    public void validateWrongInputFreeAgents() throws Exception{
        String wrongInputFreeAgents = "Henry101,Henry1,Henry2,Henry3,Henry4,Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19";
        Exception thrown = assertThrows(Exception.class,
                ()->{
                    testClassObject.validateInputFreeAgents(wrongInputFreeAgents, leagueObjectModelMocks.get20FreeAgentArrayMock());
                });
        assertTrue(thrown.getMessage().equals("Free agent Henry101 Doesn't Exist"));
    }

    @Test
    public void validate20InputFreeAgents() throws Exception{
        String inputFreeAgents = "Henry5,Henry6,Henry7,Henry8,Henry9,Henry10,Henry11,Henry12,Henry13,Henry14,Henry15,Henry16,Henry17,Henry18,Henry19";
        ArrayList<IPlayer> freeAgent = new ArrayList<>();
        Exception thrown = assertThrows(Exception.class,
                ()->{
                    testClassObject.validateInputFreeAgents(inputFreeAgents, leagueObjectModelMocks.get20FreeAgentArrayMock());
                });
        assertTrue(thrown.getMessage().equals("Enter 20 Free Agents or enter Exit to Quit game"));
    }

    @Test
    public void findGeneralManager(){
        String generalManager = testClassObject.findGeneralManager(leagueObjectModelMocks.getManagers() , "Karen Potam");
        assertTrue(generalManager.equals("Karen Potam"));

        String wronggeneralManager = testClassObject.findGeneralManager(leagueObjectModelMocks.getManagers() , "Wrong generalmanager");
        assertTrue(wronggeneralManager == null);
    }

    @Test
    public void findCoach(){
        String coach = testClassObject.findCoach(leagueObjectModelMocks.getCoaches() , "Todd McLellan");
        assertTrue(coach.equals("Todd McLellan"));

        String wrongcoach = testClassObject.findCoach(leagueObjectModelMocks.getCoaches() , "Wrong coach");
        assertTrue(wrongcoach == null);
    }
}
