package dhl.businessLogic.gameSimulation;

import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;
import java.util.List;

public class PenaltyObserver extends IGameObserver {

    List<Integer> penalties = new ArrayList<>();

    public PenaltyObserver(ISubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public List<Integer> getObserverPenalties() {
        return penalties;
    }

    public void update() {
        penalties.add(subject.getPenalties());
    }

    public double print() {
        Integer countPenalties = 0;
        Double averagePenalties = 0.0;

        for (int i = 0; i < penalties.size(); i++) {
            countPenalties = countPenalties + penalties.get(i);
        }
        averagePenalties = Double.valueOf(countPenalties / penalties.size());
        IUserInputOutput userInputOutput = UserInputOutput.getInstance();
        userInputOutput.printMessage("Penalties: " + averagePenalties);

        return averagePenalties;
    }
}