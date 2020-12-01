package dhl.businessLogic.traning;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;

public class TrainingFactory extends TrainingAbstractFactory {

    public ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig) {
        return new Training(injurySystem, gameConfig);
    }
}
