package dhl.businessLogic.gameSimulation;

public abstract class GameSimulationAbstractFactory {
    private static GameSimulationAbstractFactory uniqueInstance = null;

    protected GameSimulationAbstractFactory() {

    }

    public static GameSimulationAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new GameSimulationFactory();
        }
        return uniqueInstance;
    }

    public abstract IGameSimulation createGameSimulation();

    public abstract IGameSimulationAlgorithm createGameSimulationAlgorithm();

    public abstract IGameObserver createGoalObserver(ISubject subject);

    public abstract IGameObserver createPenaltyObserver(ISubject subject);

    public abstract IGameObserver createSaveObserver(ISubject subject);

    public abstract IGameObserver createShotObserver(ISubject subject);

    public abstract ISubject createSubject();
}
