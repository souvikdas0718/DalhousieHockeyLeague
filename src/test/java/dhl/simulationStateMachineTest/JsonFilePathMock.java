package dhl.simulationStateMachineTest;

import dhl.simulationStateMachine.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

    String filePath = "src//test//java//dhl//simulationStateMachineTest//MockData.json";
    @Override
    public String getFilePath() {
        return filePath;
    }
}