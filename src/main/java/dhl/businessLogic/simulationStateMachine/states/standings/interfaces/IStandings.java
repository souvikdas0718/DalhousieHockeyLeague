package dhl.businessLogic.simulationStateMachine.states.standings.interfaces;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public interface IStandings {
    public IConference getTeamConference();

    public void setTeamConference(IConference teamConference);

    public IDivision getTeamDivision();

    public void setTeamDivision(IDivision teamDivision);

    public ITeam getTeam();

    public void setTeam(ITeam team);

    public int getGamesPlayed();

    public void setGamesPlayed(int gamesPlayed);

    public int getWins();

    public void setWins(int wins);

    public int getLoss();

    public void setLoss(int loss);

    public int getPoints();

    public void setPoints(int points);

}
