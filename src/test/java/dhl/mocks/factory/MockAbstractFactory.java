package dhl.mocks.factory;

import dhl.mocks.JsonFilePathMock;
import dhl.mocks.LeagueObjectModel20TeamMocks;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.mocks.RegularSeasonStandingListMocks;
import dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest.PlayerDraftMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public abstract class MockAbstractFactory {

    private static MockAbstractFactory uniqueInstance = null;

    protected MockAbstractFactory() {

    }

    public static MockAbstractFactory instance() {
        if (null == uniqueInstance) {
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

    public abstract LeagueObjectModelMocks getLeagueObjectModelMock();

    public abstract LeagueObjectModel20TeamMocks getLeagueObjectModel20TeamMock();

    public abstract RegularSeasonStandingListMocks getRegularSeasonStandingListMock();

    public abstract PlayerDraftMock getPlayerDraftMock();
}
