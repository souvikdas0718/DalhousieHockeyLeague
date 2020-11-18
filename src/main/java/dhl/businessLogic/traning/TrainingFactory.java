package dhl.businessLogic.traning;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface TrainingFactory {
    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel objLeagueObjectModel) throws Exception;
}
