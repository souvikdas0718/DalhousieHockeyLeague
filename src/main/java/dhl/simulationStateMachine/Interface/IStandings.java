package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;

public interface IStandings {
    public IConference getTeamConference();

    public void setTeamConference(IConference teamConference);

    public IDivision getTeamDivision();

    public void setTeamDivision(IDivision teamDivision);

    public ITeam getTeamName();

    public void setTeamName(ITeam teamName);

    public int getGamesPlayed();

    public void setGamesPlayed(int gamesPlayed);

    public int getWins();

    public void setWins(int wins);

    public int getLoss();

    public void setLoss(int loss);

    public int getPoints();

    public void setPoints(int points);

}
