package dhl.businessLogic.traning;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.traning.interfaces.ITraining;

public class TrainingFactory implements TrainingAbstractFactory {

    public ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig) {
        return new Training(injurySystem, gameConfig);
    }
}
