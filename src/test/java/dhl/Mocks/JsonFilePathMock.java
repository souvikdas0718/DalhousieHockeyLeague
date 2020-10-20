package dhl.Mocks;

import dhl.importJson.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

//    String filePath = "src//test//java//dhl//simulationStateMachineTest//MockData.json";
    String filePath = "src//test//java//dhl//simulationStateMachineTest//MockData2.json";

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