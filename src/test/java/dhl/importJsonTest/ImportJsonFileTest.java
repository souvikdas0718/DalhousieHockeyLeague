package dhl.importJsonTest;


import dhl.importJsonTest.mocks.JsonFilePathMock;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImportJsonFileTest {

    IJsonFilePath filePathMock;
    ImportJsonAbstractFactory importJsonFactory;
    @BeforeEach
    public void initObject(){
        importJsonFactory = ImportJsonAbstractFactory.instance();
        filePathMock = JsonFilePathMock.instance();
    }


    @Test
    public void getJsonObjectTest(){
        IImportJsonFile importJsonFile = importJsonFactory.createImportJsonFile(filePathMock.getFilePath());
        assertFalse( importJsonFile.getJsonObject().isEmpty() );
    }

    @Test
    public void getJsonIntoStringTest() throws IOException {
        IImportJsonFile importJsonFile = importJsonFactory.createImportJsonFile(filePathMock.getFilePath());
        assertFalse( importJsonFile.getJsonIntoString(filePathMock.getFilePath()).isEmpty() );
    }

}
