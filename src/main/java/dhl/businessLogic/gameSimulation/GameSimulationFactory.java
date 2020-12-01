package dhl.businessLogic.gameSimulation;

public class GameSimulationFactory extends GameSimulationAbstractFactory {

    public IGameSimulation createGameSimulation() {
        return new GameSimulation();
    }

    public IGameSimulationAlgorithm createGameSimulationAlgorithm() {
        return new GameSimulationAlgorithm();
    }

    public IGameObserver createGoalObserver(ISubject subject) {
        return new GoalObserver(subject);
    }

    public IGameObserver createPenaltyObserver(ISubject subject) {
        return new PenaltyObserver(subject);
    }

    public IGameObserver createSaveObserver(ISubject subject) {
        return new SaveObserver(subject);
    }

    public IGameObserver createShotObserver(ISubject subject) {
        return new ShotObserver(subject);
    }

    public ISubject createSubject() {
        return new Subject();
    }
}
