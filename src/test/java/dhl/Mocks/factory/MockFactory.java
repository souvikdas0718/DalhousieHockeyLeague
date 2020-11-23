package dhl.Mocks.factory;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.MockDeserializeLeagueObjectModel;
import dhl.Mocks.MockSerializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;


public class MockFactory extends MockAbstractFactory {

    public MockFactory(){

    }

    public JsonFilePathMock getJsonFilePath(){
        return new  JsonFilePathMock();
    }

    public ISerializeLeagueObjectModel getMockSerialize(){
        return new MockSerializeLeagueObjectModel();
    }

    public IDeserializeLeagueObjectModel getMockDeserialize(){
        return new MockDeserializeLeagueObjectModel();
    }

}
