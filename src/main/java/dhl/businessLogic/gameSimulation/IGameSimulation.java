package dhl.businessLogic.gameSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IGameSimulation {
    public void simulateGame(ITeam teamOne, ITeam teamTwo);

    public String getWinner();
}
