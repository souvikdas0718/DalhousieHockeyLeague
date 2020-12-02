package dhl.businessLogic.gameSimulation;

import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;
import java.util.List;

public class GoalObserver extends IGameObserver {

    List<Integer> goals = new ArrayList<>();

    public GoalObserver(ISubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public List<Integer> getObserverGoals() {
        return goals;
    }

    public void update() {
        goals.add(subject.getGoals());
    }

    public double print() {
        Integer countGoals = 0;
        Double averageGoals = 0.0;

        for (int i = 0; i < goals.size(); i++) {
            countGoals = countGoals + goals.get(i);
        }
        averageGoals = Double.valueOf(countGoals / goals.size());
        IUserInputOutput userInputOutput = UserInputOutput.getInstance();
        userInputOutput.printMessage("Goals: " + averageGoals);
        return averageGoals;
    }
}