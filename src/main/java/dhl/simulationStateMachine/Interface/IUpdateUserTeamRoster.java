package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface IUpdateUserTeamRoster {

    public void dropSkater(ITeam team, ILeagueObjectModel leagueObjectModel);

    public void dropGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    public void addGoalie(ITeam team, ILeagueObjectModel leagueObjectModel);

    public void addSkater(ITeam team, ILeagueObjectModel leagueObjectModel);
}
