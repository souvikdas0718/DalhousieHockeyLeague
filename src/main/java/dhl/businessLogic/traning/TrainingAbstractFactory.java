package dhl.businessLogic.traning;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;

public abstract class TrainingAbstractFactory {

    private static TrainingAbstractFactory uniqueInstance = null;

    protected TrainingAbstractFactory() {

    }

    public static TrainingAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new TrainingFactory();
        }
        return uniqueInstance;
    }

    public abstract ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig);
}
