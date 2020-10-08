package dhl.simulationStateMachineTest;


import dhl.simulationStateMachine.CreateLeagueObjectModel;
import dhl.simulationStateMachine.ImportJsonFile;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateLeagueObjectModelTest {

    JSONObject leagueObject;
    CreateLeagueObjectModel testClassObject;
    JsonFilePathMock filePathMock;

    @BeforeEach
    public void initObject(){
        this.filePathMock = new JsonFilePathMock();
        ImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        this.leagueObject = importJsonFile.getJsonObject();
        this.testClassObject = new CreateLeagueObjectModel();
    }

    @Test
    public void checkJsonArrayTest(){
        String conferenceArrayKey = filePathMock.getConferenceArrayKey();
        assertTrue(testClassObject.checkJsonArray(leagueObject, conferenceArrayKey));

        String freeAgentArrayKey = filePathMock.getFreeAgentArrayKey();
        assertTrue(testClassObject.checkJsonArray(leagueObject, freeAgentArrayKey));

        assertFalse(testClassObject.checkJsonArray(leagueObject , "invalidArrayKey"));
    }

    @Test
    public void getLeagueObjectModel() throws Exception {
        CreateLeagueObjectModel testObject = new CreateLeagueObjectModel(leagueObject);
        assertTrue(testObject.getLeagueObjectModel() != null );
    }

}
