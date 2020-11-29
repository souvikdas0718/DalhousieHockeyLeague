package dhl.businessLogic.gameSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.HashMap;

public interface IGameSimulationAlgorithm {
    public HashMap<String, Integer> getResultOfGame(ITeam teamOne, ITeam teamTwo);
}
