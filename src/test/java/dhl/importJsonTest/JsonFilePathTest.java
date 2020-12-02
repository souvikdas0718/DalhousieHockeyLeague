package dhl.importJsonTest;

import dhl.importJsonTest.mocks.JsonFilePathMock;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.importJson.JsonFilePath;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFilePathTest {

    ImportJsonAbstractFactory jsonFactory;
    IJsonFilePath testClassObject;
    IJsonFilePath jsonDataMock;

    @BeforeEach
    public void initObject() {
        jsonFactory = ImportJsonAbstractFactory.instance();
        testClassObject = jsonFactory.createJsonFilePath();
        jsonDataMock = JsonFilePathMock.instance();
    }

    @Test
    public void validatePathTest() {
        assertFalse( ((JsonFilePath)testClassObject).validatePath("Wrong Json File Path"));
        assertTrue(((JsonFilePath)testClassObject).validatePath(jsonDataMock.getFilePath()));
    }
}
