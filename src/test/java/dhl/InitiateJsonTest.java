package dhl;


import dhl.simulationStateMachine.InitiateJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitiateJsonTest {

    @Test
    public void importJsonFile(){
        JsonFilePathMock filePathMock = new JsonFilePathMock();
        InitiateJson jsonData = new InitiateJson(filePathMock.getFilePath());
        assertTrue((jsonData.importJsonFile()).length()> 0);
    }
}
