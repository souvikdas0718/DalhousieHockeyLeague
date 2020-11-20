package dhl.Mocks;

import dhl.InputOutput.importJson.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

    private static final String FILEPATH = "src/test/java/dhl/Mocks/MockData2.json";
    public static final String INCORRECTJSONFILEPATH = "src/test/java/dhl/Mocks/IncorrectMockData.json";


    public String getFilePath() {
        return FILEPATH;
    }

    public String getIncorrectJsonfilepath() {
        return INCORRECTJSONFILEPATH;
    }

    public String getLeagueArrayKey() {
        return "leagueName";
    }

    public String getConferenceArrayKey() {
        return "conferences";
    }

    public String getFreeAgentArrayKey() {
        return "freeAgents";
    }

}