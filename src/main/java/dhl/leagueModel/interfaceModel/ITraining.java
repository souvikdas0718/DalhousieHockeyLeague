package dhl.leagueModel.interfaceModel;

import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.ArrayList;
import java.util.List;

public interface ITraining {
    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel objLeagueObjectModel) throws Exception;
}
