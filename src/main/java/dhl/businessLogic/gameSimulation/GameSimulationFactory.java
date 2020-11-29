package dhl.businessLogic.gameSimulation;

public class GameSimulationFactory extends GameSimulationAbstractFactory {

    public IGameSimulation createGameSimulation() {
        return new GameSimulation();
    }

    public IGameSimulationAlgorithm createGameSimulationAlgorithm() {
        return new GameSimulationAlgorithm();
    }
}
