package dhl;

import dhl.simulationStateMachine.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {
    @Override
    public String getFilePath() {
        return "MockData.json";
    }
}
