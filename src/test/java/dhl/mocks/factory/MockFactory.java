package dhl.mocks.factory;

import dhl.mocks.*;
import dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest.PlayerDraftMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;


public class MockFactory extends MockAbstractFactory {

    public MockFactory() {

    }

    public JsonFilePathMock getJsonFilePath() {
        return new JsonFilePathMock();
    }

    public ISerializeLeagueObjectModel getMockSerialize() {
        return new MockSerializeLeagueObjectModel();
    }

    public IDeserializeLeagueObjectModel getMockDeserialize() {
        return new MockDeserializeLeagueObjectModel();
    }

    public LeagueObjectModelMocks getLeagueObjectModelMock() {
        return new LeagueObjectModelMocks();
    }

    public LeagueObjectModel20TeamMocks getLeagueObjectModel20TeamMock() {
        return new LeagueObjectModel20TeamMocks();
    }

    public RegularSeasonStandingListMocks getRegularSeasonStandingListMock() {
        return new RegularSeasonStandingListMocks();
    }

    public PlayerDraftMock getPlayerDraftMock() {
        return new PlayerDraftMock();
    }

}
