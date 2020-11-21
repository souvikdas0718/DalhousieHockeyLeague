package dhl.businessLogic.simulationStateMachine.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IUpdateUserTeamRoster {

    void dropSkater(ITeam team, ILeagueObjectModel leagueObjectModel);

    void dropGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    void addGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    void addSkater(ITeam team, ILeagueObjectModel leagueObjectModel);
}
