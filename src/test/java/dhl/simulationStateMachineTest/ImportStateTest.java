package dhl.simulationStateMachineTest;

import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.ImportState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImportStateTest {
    ImportState testClassObject;
    GameContext ourGame;
    LeagueObjectModelMocks mock;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new ImportState(ourGame);
        mock = new LeagueObjectModelMocks();
    }

    @Test
    public void findTeamTest(){
        String team = "Ontario";
        Assertions.assertTrue( testClassObject.findTeam(mock.getLeagueObjectMock() , team) != null);
        Assertions.assertTrue( testClassObject.findTeam(mock.getLeagueObjectMock() , "Wrong Team") == null);
    }
}

