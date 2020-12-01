package dhl.businessLogic.gameSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject implements ISubject {

    private List<IGameObserver> observers = new ArrayList<IGameObserver>();
    private Integer goals;
    private Integer saves;
    private Integer shots;
    private Integer penalties;

    public Integer getGoals() {
        return goals;
    }

    public Integer getSaves() {
        return saves;
    }

    public Integer getShots() {
        return shots;
    }

    public Integer getPenalties() {
        return penalties;
    }

    public List<IGameObserver> getObservers() {
        return observers;
    }

    public void setState(HashMap<String, Integer> state) {

        for (Map.Entry<String, Integer> set : state.entrySet()) {
            if (set.getKey().equals("Shots")) {
                this.shots = set.getValue();
            } else if (set.getKey().equals("Goals")) {
                this.goals = set.getValue();
            } else if (set.getKey().equals("Penalties")) {
                this.penalties = set.getValue();
            } else if (set.getKey().equals("Saves")) {
                this.saves = set.getValue();
            }
        }
        notifyAllObservers();
    }

    public void attach(IGameObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (IGameObserver observer : observers) {
            observer.update();
        }
    }
}