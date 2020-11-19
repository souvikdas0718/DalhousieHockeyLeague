package dhl.businessLogic.traning;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.traning.interfaces.ITraining;
import dhl.businessLogic.aging.interfaceAging.IInjury;

public interface TrainingAbstractFactory {
    public ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig);
}
