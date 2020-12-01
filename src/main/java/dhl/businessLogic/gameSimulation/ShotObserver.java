package dhl.businessLogic.gameSimulation;

import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;
import java.util.List;

public class ShotObserver extends IGameObserver {

    List<Integer> shots = new ArrayList<>();

    public ShotObserver(ISubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public List<Integer> getObserverShots() {
        return shots;
    }

    public void update() {
        shots.add(subject.getShots());
    }

    public double print() {
        Integer countShots = 0;
        Double averageShots = 0.0;

        for (int i = 0; i < shots.size(); i++) {
            countShots = countShots + shots.get(i);
        }
        averageShots = Double.valueOf(countShots / shots.size());
        IUserInputOutput userInputOutput = UserInputOutput.getInstance();
        userInputOutput.printMessage("Shots: " + averageShots);

        return averageShots;
    }
}