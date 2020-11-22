package dhl.Mocks.factory;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.MockDeserializeLeagueObjectModel;
import dhl.Mocks.MockSerializeLeagueObjectModel;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public abstract class MockAbstractFactory {

    private static MockAbstractFactory uniqueInstance = null;

    protected MockAbstractFactory() {

    }

    public static MockAbstractFactory instance() {
        if (null == uniqueInstance)
        {
            uniqueInstance = new MockFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(MockAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract JsonFilePathMock getJsonFilePath();

    public abstract ISerializeLeagueObjectModel getMockSerialize();

    public abstract IDeserializeLeagueObjectModel getMockDeserialize();
}
