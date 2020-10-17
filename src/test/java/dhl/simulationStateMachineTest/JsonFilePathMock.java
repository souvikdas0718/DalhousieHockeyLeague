package dhl.simulationStateMachineTest;

import dhl.inputOutput.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

    String filePath = "src//test//java//dhl//simulationStateMachineTest//MockData.json";

    @Override
    public String getFilePath() {
        return filePath;
    }

    public String getLeagueArrayKey(){
        return "leagueName";
    }

    public String getConferenceArrayKey(){
        return "conferences";
    }

    public String getFreeAgentArrayKey(){
        return "freeAgents";
    }

}