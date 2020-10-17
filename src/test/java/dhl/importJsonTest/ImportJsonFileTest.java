package dhl.importJsonTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.importJson.ImportJsonFile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImportJsonFileTest {

    @Test
    public void getJsonObjectTest() throws Exception {
        JsonFilePathMock filePathMock = new JsonFilePathMock();
        ImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        assertFalse( (importJsonFile.getJsonObject()).isEmpty() );
    }

}
