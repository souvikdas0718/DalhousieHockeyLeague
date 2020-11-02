package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface IUpdateUserTeamRoster {

    void dropSkater(ITeam team, ILeagueObjectModel leagueObjectModel);

    void dropGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    void addGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    void addSkater(ITeam team, ILeagueObjectModel leagueObjectModel);
}
