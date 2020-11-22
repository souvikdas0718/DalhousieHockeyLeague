package dhl.businessLogic.simulationStateMachine.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IUpdateUserTeamRoster {

    void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);

    void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);
}
