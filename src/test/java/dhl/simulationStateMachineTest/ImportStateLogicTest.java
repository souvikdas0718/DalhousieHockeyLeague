package dhl.simulationStateMachineTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.IJsonFilePath;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.states.ImportStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImportStateLogicTest {

    ImportStateLogic testClassObject;
    GameContext ourGame;
    LeagueObjectModelMocks leagueObjectModelMock;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new ImportStateLogic();
        leagueObjectModelMock = new LeagueObjectModelMocks();
    }

    @Test
    public void importAndGetLeagueObjectTest() throws Exception {
        IJsonFilePath filePath = new JsonFilePathMock();
        IGameConfig gameConfig = null;
        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        leagueObjectModel = testClassObject.importAndGetLeagueObject(filePath.getFilePath(),gameConfig,newInMemoryLeague);
        Assertions.assertEquals("Dalhousie Hockey Leaguen1",leagueObjectModel.getLeagueName());
    }

    @Test
    public void findTeamTest(){
        String team = "Ontario";
        Assertions.assertTrue( testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock() , team) != null);
        Assertions.assertTrue( testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock() , "Wrong Team") == null);
    }
}

