package dhl.businessLogic.teamRosterUpdater.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface ITeamRosterUpdater {

    public void validateTeamRoster(ITeam team, ILeagueObjectModel leagueObjectModel);

    public void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);

    public void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);
}
