package dhl.businessLogic.gameSimulation;

import dhl.inputOutput.ui.UserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.ArrayList;
import java.util.List;

public class SaveObserver extends IGameObserver {

    List<Integer> saves = new ArrayList<>();

    public SaveObserver(ISubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public List<Integer> getObserverSaves() {
        return saves;
    }

    public void update() {
        saves.add(subject.getSaves());
    }

    public double print() {
        Integer countSaves = 0;
        Double averageSaves = 0.0;

        for (int i = 0; i < saves.size(); i++) {
            countSaves = countSaves + saves.get(i);
        }
        averageSaves = Double.valueOf(countSaves / saves.size());
        IUserInputOutput userInputOutput = UserInputOutput.getInstance();
        userInputOutput.printMessage("Saves: " + averageSaves);

        return averageSaves;
    }
}