package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;

public abstract class GameSimulationMockAbstractFactory {
    private static GameSimulationMockAbstractFactory uniqueInstance = null;

    protected GameSimulationMockAbstractFactory() {

    }

    public static GameSimulationMockAbstractFactory instance() {
        if (null == uniqueInstance)
        {
            uniqueInstance = new GameSimulationMockFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(GameSimulationMockAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract MapStatsMock createMapStatsMock();
}
