package dhl.businessLogicTest.StateMachineTest;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.IJsonFilePath;
import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.States.ImportStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImportStateLogicTest {

    ImportStateLogic testClassObject;
    GameContext ourGame;
    LeagueObjectModelMocks leagueObjectModelMock;

    @BeforeEach
    public void initObject() {
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
        leagueObjectModel = testClassObject.importAndGetLeagueObject(filePath.getFilePath(), gameConfig, newInMemoryLeague);
        Assertions.assertEquals("Dalhousie Hockey League", leagueObjectModel.getLeagueName());
    }

    @Test
    public void findTeamTest() {
        String team = "Ontario";
        Assertions.assertTrue(testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock(), team) != null);
        Assertions.assertTrue(testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock(), "Wrong Team") == null);
    }
}

