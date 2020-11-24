package dhl.businessLogic.simulationStateMachine.states.standings;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

public class Standings implements IStandings {

    private IConference teamConference;
    private IDivision teamDivision;
    private ITeam team;
    private int gamesPlayed;
    private int wins;
    private int loss;
    private int points;

    public IConference getTeamConference() {
        return teamConference;
    }

    public void setTeamConference(IConference teamConference) {
        this.teamConference = teamConference;
    }

    public IDivision getTeamDivision() {
        return teamDivision;
    }

    public void setTeamDivision(IDivision teamDivision) {
        this.teamDivision = teamDivision;
    }

    public ITeam getTeam() {
        return team;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
