package dhl.simulationStateMachineTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.importJson.Interface.IGameConfig;
import dhl.importJson.Interface.IJsonFilePath;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.ImportStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImportStateLogicTest {
    ImportStateLogic testClassObject;
    GameContext ourGame;
    LeagueObjectModelMocks mock;

    @BeforeEach
    public void initObject(){
        ourGame = new GameContext();
        testClassObject = new ImportStateLogic();
        mock = new LeagueObjectModelMocks();
    }

    @Test
    public void importAndGetLeagueObjectTest() throws Exception {
        IJsonFilePath objfilePath = new JsonFilePathMock();
        IGameConfig gameConfig = null;
        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();
        objLeagueObjectModel = testClassObject.importAndGetLeagueObject(objfilePath.getFilePath(),gameConfig,newInMemoryLeague);
        Assertions.assertEquals("Dalhousie Hockey League",objLeagueObjectModel.getLeagueName());
    }

    @Test
    public void findTeamTest(){
        String team = "Ontario";
        Assertions.assertTrue( testClassObject.findTeam(mock.getLeagueObjectMock() , team) != null);
        Assertions.assertTrue( testClassObject.findTeam(mock.getLeagueObjectMock() , "Wrong Team") == null);
    }
}

