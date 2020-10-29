package dhl.Training;

import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;

import java.util.ArrayList;

public interface ITraining {
    public ILeagueObjectModel statIncrease(ILeagueObjectModel objLeagueObjectModel) throws Exception;
    public ArrayList<IPlayer> updatePlayerStats(ArrayList<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues) throws Exception;
}
