package dhl.businessLogic.traning;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.traning.Interfaces.ITraining;

public class TrainingFactory implements TrainingAbstractFactory {

    public ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig) {
        return new Training(injurySystem, gameConfig);
    }
}
