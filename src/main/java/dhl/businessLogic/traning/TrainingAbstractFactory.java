package dhl.businessLogic.traning;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.traning.Interfaces.ITraining;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;

import java.util.List;

public interface TrainingAbstractFactory {
    public ITraining createTraining(IInjury injurySystem, IGameConfig gameConfig);
}
