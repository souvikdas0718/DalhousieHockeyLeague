package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.GameSimulationAbstractFactory;

public class GameSimulationMockFactory extends GameSimulationMockAbstractFactory {

    public MapStatsMock createMapStatsMock() {
        return new MapStatsMock();
    }
}
