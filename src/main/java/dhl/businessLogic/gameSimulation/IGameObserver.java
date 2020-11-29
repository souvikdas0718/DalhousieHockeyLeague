package dhl.businessLogic.gameSimulation;

public abstract class IGameObserver {
    protected Subject subject;
    public abstract void update();
}