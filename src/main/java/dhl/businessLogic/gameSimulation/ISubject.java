package dhl.businessLogic.gameSimulation;

import java.util.HashMap;

public interface ISubject {
    public void setState(HashMap<String, Integer> state);

    public void attach(IGameObserver observer);

    public Integer getGoals();

    public Integer getSaves();

    public Integer getShots();

    public Integer getPenalties();
}
