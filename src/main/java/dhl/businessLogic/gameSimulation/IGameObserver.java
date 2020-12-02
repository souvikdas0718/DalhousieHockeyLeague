package dhl.businessLogic.gameSimulation;

public abstract class IGameObserver {
    protected ISubject subject;

    public abstract void update();

    public abstract double print();
}