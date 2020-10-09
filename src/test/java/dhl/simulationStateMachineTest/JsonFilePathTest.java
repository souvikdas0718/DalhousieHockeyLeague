package dhl.simulationStateMachineTest;

import dhl.simulationStateMachine.JsonFilePath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFilePathTest {
    JsonFilePath testClassObject;
    JsonFilePathMock jsonDataMock;

    @BeforeEach
    public void initObject(){
        testClassObject = new JsonFilePath();
        jsonDataMock = new JsonFilePathMock();
    }

    @Test
    public void validatePathTest(){
        assertFalse(testClassObject.validatePath("Wrong Json File Path"));
        System.out.println();
        assertTrue( testClassObject.validatePath(jsonDataMock.getFilePath()) );
    }
}
