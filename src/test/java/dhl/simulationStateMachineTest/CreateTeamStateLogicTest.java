package dhl.simulationStateMachineTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelTests.MockDatabase;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.CreateTeamStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTeamStateLogicTest {
    GameContext ourGame;
    CreateTeamStateLogic testClassObject;
    LeagueObjectModelMocks mocks;
    private ILeagueObjectModel inMemoryLeague;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new CreateTeamStateLogic();
        mocks = new LeagueObjectModelMocks();
        inMemoryLeague = mocks.getLeagueObjectMock();
    }
    @Test
    public void saveleagueObjectTest() throws Exception {
        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();
        ILeagueObjectModelDB mockDb=new MockDatabase();
        ILeagueObjectModelValidation validation = new LeagueObjectModelValidation();
        ITeam team= new Team("Ontario1","Mathew1",mocks.getSingleCoach(),new ArrayList<>());
        LeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput(inMemoryLeague.getLeagueName(), "Western", "Atlantic", team,validation);
        objLeagueObjectModel = testClassObject.saveleagueObject( ourGame,inMemoryLeague,leagueObjectModelInput);
        Assertions.assertEquals("Dhl",objLeagueObjectModel.getLeagueName());
    }

    @Test
    public void findConferenceTest(){
        IConference conferenceTest = testClassObject.findConference(mocks.getConferenceArrayMock() , "Mock Conference1");
        assertTrue(conferenceTest.getConferenceName().equals("Mock Conference1"));

        conferenceTest = testClassObject.findConference(mocks.getConferenceArrayMock() , "Wrong Conference1");
        assertTrue(conferenceTest == null);
    }

    @Test
    public void findDivisionTest(){
        IDivision divisionTest = testClassObject.findDivision(mocks.getDivisionArrayMock() , "Mock Division2");
        assertTrue(divisionTest.getDivisionName().equals("Mock Division2"));

        divisionTest = testClassObject.findDivision(mocks.getDivisionArrayMock() , "Wrong DivisionName");
        assertTrue(divisionTest == null);

    }

    @Test
    public void findFreeAgent(){
        IPlayer freeAgentTest = testClassObject.findFreeAgent(mocks.getFreeAgentArrayMock() , "Mock Free Agent 1");
        assertTrue(freeAgentTest.getPlayerName().equals("Mock Free Agent 1"));

        freeAgentTest = testClassObject.findFreeAgent(mocks.getFreeAgentArrayMock() , "Wrong Free Agent");
        assertTrue(freeAgentTest == null);

    }

    @Test
    public void createNewTeamObjectTest() throws Exception {
        ITeam newlyCreatedTeam= new Team();
        ITeam teamWithoutPlayers= new Team("testTeam", "testGenManager", mocks.getSingleCoach(),new ArrayList<>());
        newlyCreatedTeam = testClassObject.createNewTeamObject(mocks.get20FreeAgentArrayMock(),teamWithoutPlayers );
        Assertions.assertEquals(20,newlyCreatedTeam.getPlayers().size());
    }
}
