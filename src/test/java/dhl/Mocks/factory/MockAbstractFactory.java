package dhl.Mocks.factory;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Mocks.RegularSeasonStandingListMocks;
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
