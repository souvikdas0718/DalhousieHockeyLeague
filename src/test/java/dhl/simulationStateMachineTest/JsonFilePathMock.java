package dhl.simulationStateMachineTest;

import dhl.simulationStateMachine.Interface.IJsonFilePath;

public class JsonFilePathMock implements IJsonFilePath {

    String filePath = "N://STUDIES//MACS//SEM-1//CSCI5308 - Adv Topics in Software Develop//Project//csci5308//src//test//java//dhl//simulationStateMachineTest//MockData.json";
    @Override
    public String getFilePath() {
        return filePath;
    }
}