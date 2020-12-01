package dhl.mocks;

import dhl.inputOutput.importJson.interfaces.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

    private static final String FILEPATH = "src/test/java/dhl/mocks/MockData2.json";
    public static final String INCORRECTJSONFILEPATH = "src/test/java/dhl/mocks/IncorrectMockData.json";


    public String getFilePath() {
        return FILEPATH;
    }

    public String getIncorrectJsonfilepath() {
        return INCORRECTJSONFILEPATH;
    }

}