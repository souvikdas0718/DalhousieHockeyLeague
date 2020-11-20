package dhl.businessLogicTest.StateMachineTest;

import dhl.InputOutput.importJson.ImportJsonFile;
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

import java.io.IOException;

public class ImportStateLogicTest {
    private static final String SCHEMAFILEPATH = "src/main/java/dhl/InputOutput/importJson/jsonSchema/schema.json";
    ImportStateLogic testClassObject;
    GameContext ourGame;
    LeagueObjectModelMocks leagueObjectModelMock;
    JsonFilePathMock filePathMock;
    ImportJsonFile importJsonFile;

    @BeforeEach
    public void initObject() {
        filePathMock = new JsonFilePathMock();
        importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        ourGame = new GameContext();
        testClassObject = new ImportStateLogic();
        leagueObjectModelMock = new LeagueObjectModelMocks();
    }

    @Test
    public void importAndGetLeagueObjectTest() throws Exception {
        IJsonFilePath filePath = new JsonFilePathMock();
        ILeagueObjectModel leagueObjectModel = testClassObject.importAndGetLeagueObject(filePath.getFilePath());
        Assertions.assertEquals("Dalhousie Hockey League", leagueObjectModel.getLeagueName());
    }

    @Test
    public void findTeamTest() {
        String team = "Ontario";
        Assertions.assertTrue(testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock(), team) != null);
        Assertions.assertTrue(testClassObject.findTeam(leagueObjectModelMock.getLeagueObjectMock(), "Wrong Team") == null);
    }

    @Test
    public void jsonSchemaValidationTrueTest() throws IOException {

        String leagueJson = importJsonFile.getJsonIntoString(filePathMock.getFilePath());
        String schemaJson = importJsonFile.getJsonIntoString(SCHEMAFILEPATH);
        Assertions.assertTrue(testClassObject.jsonSchemaValidation(leagueJson,schemaJson));
    }

    @Test
    public void jsonSchemaValidationFalseTest() throws IOException {
        String leagueJson = "{\"leagueName\":\"\"}";
        String schemaJson = importJsonFile.getJsonIntoString(SCHEMAFILEPATH);
        testClassObject.jsonSchemaValidation(leagueJson,schemaJson);
        Assertions.assertFalse(testClassObject.jsonSchemaValidation(leagueJson,schemaJson));
    }
}

