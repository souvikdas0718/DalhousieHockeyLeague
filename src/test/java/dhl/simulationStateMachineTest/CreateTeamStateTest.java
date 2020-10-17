package dhl.simulationStateMachineTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.CreateTeamState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTeamStateTest {
    GameContext ourGame;
    CreateTeamState testClassObject;
    LeagueObjectModelMocks mocks;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new CreateTeamState(ourGame);
        mocks = new LeagueObjectModelMocks();
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
}
