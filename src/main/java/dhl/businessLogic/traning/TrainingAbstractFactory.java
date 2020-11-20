package dhl.businessLogic.traning;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.traning.interfaces.ITraining;
import dhl.businessLogic.aging.interfaceAging.IInjury;

public abstract class TrainingAbstractFactory {
    private static TrainingAbstractFactory uniqueInstance = null;

    protected TrainingAbstractFactory(){

    }

    public static TrainingAbstractFactory instance(){
        if (null == uniqueInstance){
            uniqueInstance = new TrainingFactory();
        }
        return uniqueInstance;
    }

    public abstract ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig);
}
